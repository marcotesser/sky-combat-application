/**
 * Project:  EverBuilds
 * File:  GameView.kt
 * Author:  Samuele Sartor
 * Created:  2021-02-03
 * Version:  1.0.0
 * ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
 * Copyright 2021 EverBuild Group.
 * Licensed under the MIT License.  See License.txt in the project root for license information.
 * ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
 *
 */
package com.skycombat.game


import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.*
import android.widget.TextView
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.skycombat.game.model.*
import com.skycombat.game.model.factory.EnemyFactory
import com.skycombat.game.model.factory.LifePowerUpFactory
import com.skycombat.game.model.factory.GhostFactory
import com.skycombat.game.panel.FPSPanel
import com.skycombat.game.panel.GamePanel
import com.skycombat.game.panel.UPSPanel
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList
import com.amplifyframework.datastore.generated.model.GameRoom
import com.amplifyframework.datastore.generated.model.Player
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.powerup.PowerUp
import com.skycombat.game.model.support.Drawable
import com.skycombat.game.model.support.GUIElement
import java.util.stream.Stream


/**
 * Represents the Game View
 * @param context : the context onto which the game will be drawn
 * @param MAX_WIDTH : the max width of the gameview
 * @param MAX_HEIGHT : the max height of the gameview
 */
 class GameRoomView(context: Context, private val MAX_WIDTH: Float, private val MAX_HEIGHT: Float)
    : GameView(context, MAX_WIDTH, MAX_HEIGHT) {
    override val GAME_OVER_LISTENERS : ArrayList<GameOverListener> = ArrayList()
    override val ENEMY_FACTORY = EnemyFactory(this);
    override val LIFE_POWERUP_FACTORY = LifePowerUpFactory(this);
    val GHOST_FACTORY = GhostFactory(this)

    override var enemies : CopyOnWriteArrayList<Enemy>    = CopyOnWriteArrayList();
    private var ghosts : CopyOnWriteArrayList<Ghost> = CopyOnWriteArrayList()
    override var powerUps : CopyOnWriteArrayList<PowerUp> = CopyOnWriteArrayList();

    override var panels : CopyOnWriteArrayList<GamePanel> = CopyOnWriteArrayList();
    override val startTime = System.currentTimeMillis();

    val currentPlayer = GameSession.player
    var otherPlayers = GameSession.otherPlayers

    init {
        holder.addCallback(this);
        focusable = View.FOCUSABLE;

        panels.add(FPSPanel(20F, MAX_HEIGHT/2, gameLoop, this ))
        panels.add(UPSPanel(20F, MAX_HEIGHT/2 + 100, gameLoop, this ))

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                var temp : ArrayList<com.amplifyframework.datastore.generated.model.Player> = ArrayList()
                Amplify.API.query(
                    ModelQuery.list(
                        Player::class.java,
                        Player.GAMEROOM.eq(GameSession.GameRoom?.id)
                    ),
                    { response ->
                                if(response != null)
                                response.data.forEach { p ->
                                    var i=0
                                    GameSession.otherPlayers?.forEach { el -> if((el.id).toString() == (p.id).toString()) {
                                        var ghost = Player.builder()
                                            .name(el?.name)
                                            .id(el?.id)
                                            .lastinteraction(p.lastinteraction)
                                            .score(p.score)
                                            .dead(p.dead)
                                            .positionX(p.positionX)
                                            .positionY(p.positionY)
                                            .gameroom(el?.gameroom)
                                            .build()

                                        temp.add(ghost)

                                        i++
                                        }
                                    }

                                    GameSession.otherPlayers = null
                                    GameSession.otherPlayers = temp

                                    if(i==0) {
                                        val cumpa: Player = p
                                        GameSession.otherPlayers?.add(cumpa)
                                    }
                                }

                        Log.i("idk", response.toString())
                    },
                    { error -> Log.e("MyAmplifyApp", "Query failure", error) }
                )
            }
        }, 5000, 5000)
    }

    override fun draw(canvas: Canvas?) {
        if (player.getCurrentHealth() <= 0) {
            return;
        }
        super.draw(canvas)
        if(canvas != null){
            Stream.concat(
                Stream.of(player),
                Stream.of(
                    panels,
                    enemies,
                    ghosts,
                    bullets,
                    powerUps
                ).flatMap(
                    List<Drawable>::stream
                )
            ).forEach{el -> el.draw(canvas)}
        }
    }


    /**
     * Updates the view of the whole game
     * @see EnemyFactory
     * @see LifePowerUpFactory
     * @see GameOverListener
     * @see Bullet
     * @see Enemy
     * @see Player
     */
    override fun update() {

        if (player.isDead()) {
            stop()
            GAME_OVER_LISTENERS.forEach { el ->
                el.gameOver(System.currentTimeMillis() - startTime)
            }
            return;
        }
        listOf(enemies, ghosts, powerUps, bullets).forEach{
                ar -> ar.removeIf(GUIElement::shouldRemove)
        }

        if(enemies.size == 0){
            enemies .add(ENEMY_FACTORY.generate())
            powerUps.add(LIFE_POWERUP_FACTORY.generate())
        }
        powerUps
            .filter {
                    el -> el.collide(player)
            }
            .forEach{
                    el -> el.apply(player)
            }

        var new = true
        var i = 0
        GameSession.otherPlayers?.forEach { el ->
            new = true
            i = 0
            if(!el.dead) {
                while (new && i < ghosts.size)
                    if(el.id == ghosts[i].id)
                        new = false

                if(new)
                    ghosts.add(GHOST_FACTORY.generate((el.positionX).toFloat(), (el.positionY).toFloat(), 50f, (el.id).toString(), el.name))
            }
        }


        Stream.concat(enemies.stream(), Stream.of(player)).forEach{
                entity ->
            bullets
                .filter {
                        el -> entity.collide(el)
                }
                .forEach{
                        el ->
                    el.hit()
                    entity.updateHealth( -1 * el.damage.toFloat())
                }

        }

        player.update()
        enemies.forEach(Enemy::update)
        bullets.forEach(Bullet::update)
        powerUps.forEach(PowerUp::update)
        ghosts.forEach(Ghost::update)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                player.setPosition(event.x, player.positionY)

                true
            }
            else -> true;
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (gameLoop.state == Thread.State.TERMINATED) {
            val surfaceHolder = getHolder()
            surfaceHolder.addCallback(this)
            gameLoop = GameLoop(this, surfaceHolder)
        }

        gameLoop.startLoop()
    }

}