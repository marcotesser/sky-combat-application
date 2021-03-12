package com.skycombat.game.multiplayer

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Player
import com.skycombat.game.model.gui.element.ghost.Ghost

class OpponentUpdaterService(var currentPlayer: Player, var opponents : List<Pair<Player, Ghost>>) : Thread(){
    companion object{
        const val UPS = 1
    }
    private var elapsedTime : Long = 0
    private var alive = true
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
                                .and(Player.LASTINTERACTION.gt((System.currentTimeMillis() - 10000L).toInt()))
                                .and(Player.DEAD.eq(false))
                                .and(Player.ID.ne(currentPlayer.id))
                ),
                { result ->
                    Log.e("AAAA", result.toString())
                    if(result.data.items.count() > 0){
                        Log.e("FINE", "TUTTI MORTI, SPENGO THREAD OPPONENTI")
                        alive = false
                    }
                    result.data.items
                        .forEach{
                            p ->
                                Log.i("thread-multiplayer","Player ${p.name} è in pos ${p.positionX}")
                                val op = opponents.first{ pair -> pair.first.id == p.id }
                                op.second.aimedPositionX = p.positionX.toFloat() * op.second.context.width
                        }
                },
                { Log.e("MyAmplifyApp", "Query failed") }
            )
            sleep(1000L/UPS)
        }
    }
}