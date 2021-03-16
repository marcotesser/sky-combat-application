package com.skycombat.game.multiplayer

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.Player as RemotePlayer
import com.skycombat.game.model.gui.element.Player as GUIPlayer

class PlayerUpdaterService(val player : GUIPlayer, var remote: RemotePlayer) : Thread() {
    private var alive = true
    override fun run() {
        super.run()
        Log.e("debug", "PARTITO THREAD PLAYER")
        while(!player.isDead() && alive){
            remote.copyOfBuilder()
            val playerOnline = remote.copyOfBuilder()
                    .name(remote.name)
                    .id(remote.id)
                    .gameroom(remote.gameroom)
                    .positionX(player.getX().toDouble() / player.displayDimension.width.toDouble())
                    .score((Math.random() * 10000).toInt())
                    .lastinteraction(Temporal.Timestamp.now())
                    .build()
            Amplify.API.mutate(
                    ModelMutation.update(
                        playerOnline,
                        RemotePlayer.ID.eq(remote.id)
                    ),
                    {},
                    { error -> Log.e("MyAmplifyApp", "update position failed", error) }
            )
            sleep(1000L/MultiplayerSession.UPS)
        }
    }
    fun setAsDead(score: Int = 0){
        if(alive) {
            alive = false
            val playerOnline = remote.copyOfBuilder()
                    .name(remote.name)
                    .id(remote.id)
                    .gameroom(remote.gameroom)
                    .positionX(player.getX().toDouble() / player.displayDimension.width.toDouble())
                    .score(score)
                    .lastinteraction(Temporal.Timestamp.now())
                    .dead(true)
                    .build()
            Amplify.API.mutate(
                    ModelMutation.update(
                            playerOnline,
                            RemotePlayer.ID.eq(remote.id)
                    ), {
                        player.kill()
                    },
                    { error ->
                        Log.e("MyAmplifyApp", "update position failed", error)
                    }
            )
        }
    }
}
