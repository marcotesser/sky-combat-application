package com.skycombat.game.multiplayer

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.Player as RemotePlayer
import com.skycombat.game.model.gui.element.Player as GUIPlayer

class PlayerUpdaterService(val player : GUIPlayer, var remote: RemotePlayer) : Thread() {
    companion object{
        const val UPS = 1;
    }
    override fun run() {
        super.run()
        Log.e("debug", "PARTITO THREAD PLAYER")
        while(!player.isDead()){
            remote.copyOfBuilder()
            val playerOnline = remote.copyOfBuilder()
                    .name(remote.name)
                    .id(remote.id)
                    .gameroom(remote.gameroom)
                    .positionX(player.positionX.toDouble() / player.context.width.toDouble())
                    .positionY(player.positionY.toDouble() / player.context.height.toDouble())
                    .score((Math.random() * 10000).toInt())
                    .lastinteraction(Temporal.Timestamp.now())
                    .build()
            // Log.e("POSIZIONE GIOCATORE", (player.positionX.toDouble() / player.context.width.toDouble()).toString())
            // Log.e("POSIZIONE GIOCATORE", (player.positionY.toDouble() / player.context.height.toDouble()).toString())
            // Log.e("MUTATION ", ModelMutation.update(
            //         playerOnline,
            //         RemotePlayer.ID.eq(remote.id)
            // ).toString())
            Amplify.API.mutate(
                    ModelMutation.update(
                        playerOnline,
                        RemotePlayer.ID.eq(remote.id)
                    ),
                    { response ->
                        /*remote = response.data*/
                        Log.e(
                                "RISULTATI QUERY UPDATE PLAYER",
                                response.toString()
                        )
                    },
                    { error -> Log.e("MyAmplifyApp", "update position failed", error) }
            )
            sleep(1000L/UPS)
        }
    }
}