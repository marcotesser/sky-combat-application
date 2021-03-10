package com.skycombat.game.gameview

import android.content.Context
import android.graphics.Canvas
import com.skycombat.game.model.support.Drawable
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Player
import com.skycombat.game.GameSession
import com.skycombat.game.model.Ghost
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.enemy.Enemy
import com.skycombat.game.model.powerup.PowerUp
import com.skycombat.game.model.support.GUIElement
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.stream.Stream
import kotlin.collections.ArrayList

class GameViewMultiplayer(context: Context, MAX_WIDTH : Float, MAX_HEIGHT : Float )
    : GameView(context, MAX_WIDTH, MAX_HEIGHT ) {

    var ghosts : CopyOnWriteArrayList<Ghost> = CopyOnWriteArrayList()
    var otherPlayers = GameSession.otherPlayers
    val currentPlayer = GameSession.player

    init{
        configureUpdaterAmplifyGhosts()
    }

    private fun configureUpdaterAmplifyGhosts(){
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                var temp : ArrayList<com.amplifyframework.datastore.generated.model.Player> = ArrayList()
                val amp = Amplify.API.query(
                    ModelQuery.list(
                        Player::class.java,
                        Player.GAMEROOM.eq(GameSession.GameRoom?.id)
                    ),
                    { response ->
                        if(response != null){
                            response.data.forEach { p ->

                                if(p.id != currentPlayer?.id) {
                                    var ghost = Player.builder()
                                        .name(p.name)
                                        .id(p.id)
                                        .lastinteraction(p.lastinteraction)
                                        .score(p.score)
                                        .dead(p.dead)
                                        .positionX(p.positionX)
                                        .positionY(p.positionY)
                                        .gameroom(p.gameroom)
                                        .build()

                                    temp.add(ghost)
                                }
                            }
                            GameSession.otherPlayers = null
                            GameSession.otherPlayers = temp

                            GameSession.otherPlayers?.forEach { el ->
                                var new = true
                                var i = 0
                                if(!el.dead) {
                                    while (new && i < ghosts.size)
                                        if(el.id == ghosts[i].id)
                                            new = false

                                    if(new)
                                        ghosts.add(Ghost((el.id).toString()))
                                }
                            }

                        }

                        Log.i("idk", response.toString())
                    },
                    { error -> Log.e("MyAmplifyApp", "Query failure", error) }
                )
//                amp?.cancel()
            }
        }, 5000, 5000)
    }


    override fun drawEntity(canvas: Canvas?) {
        if (canvas != null) {
            Stream.concat(
                Stream.of(player),
                Stream.of(panels, enemies, bullets,ghosts, powerUps).flatMap(
                    List<Drawable>::stream
                )
            ).forEach { el -> el.draw(canvas) }
        }
    }


    override fun updateEntity(){

        listOf(enemies, ghosts, powerUps, bullets).forEach { ar ->
            ar.removeIf(GUIElement::shouldRemove)
        }

        player.update()
        enemies.forEach(Enemy::update)
        bullets.forEach(Bullet::update)
        powerUps.forEach(PowerUp::update)
        ghosts.forEach(Ghost::update)
    }




}