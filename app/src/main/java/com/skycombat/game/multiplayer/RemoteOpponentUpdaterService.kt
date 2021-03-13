package com.skycombat.game.multiplayer

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.Player
import com.skycombat.game.model.gui.element.ghost.Ghost
import java.util.concurrent.TimeUnit
import java.util.function.Predicate

class RemoteOpponentUpdaterService(var currentPlayer: Player, private var opponents : List<Pair<Player, Ghost>>) : OpponentsUpdater(){
    private var elapsedTime : Long = 0
    private var alive = true
    override fun getOpponents(): List<Ghost> {
        return opponents.map { el -> el.second }
    }

    override fun run() {
        super.run()
        Log.e("debug", "PARTITO THREAD OPPONENTS")
        elapsedTime = System.currentTimeMillis()
        super.run()
        while(alive){
            Amplify.API.query(
                ModelQuery.list(
                    Player::class.java,
                    Player.GAMEROOM.eq(currentPlayer.gameroom.id)
                        .and(Player.LASTINTERACTION.gt(
                            Temporal.Timestamp(
                                System.currentTimeMillis() - 10000L,
                                TimeUnit.MILLISECONDS
                            ))
                        )
                        .and(Player.DEAD.eq(false))
                        .and(Player.ID.ne(currentPlayer.id))
                ),
                { result ->
                    if (result.data.items.count() <= 0) {
                        Log.e("FINE", "TUTTI MORTI, SPENGO THREAD OPPONENTI")
                        alive = false
                    }
                    val present :  (Pair<Player, Ghost>) -> Boolean  = { el ->
                        result.data.items.any { res -> res.id == el.first.id }
                    }
                    val partitions = opponents
                        .partition{el -> present(el) && !el.second.isDead()}

                    // quelli presenti nella risposta dell'API
                    partitions.first.forEach{ p ->
                        val op = result.data.items.first { req -> req.id == p.first.id }
                        p.second.aimedPositionX =
                            op.positionX.toFloat() * p.second.context.width
                    }

                    // quelli non presenti nella risposta dell'API
                    partitions.second.forEach{ p ->
                            Log.e("idk","È MORTO ${p.first.name}")
                            p.second.kill()
                        }

                },
                { Log.e("MyAmplifyApp", "Query failed") }
            )
            sleep(1000L/MultiplayerSession.UPS)
        }
    }
    override fun stopUpdates(){
        this.alive = false;
    }
}