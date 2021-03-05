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
import com.skycombat.game.model.Bullet
import com.skycombat.game.model.Enemy
import com.skycombat.game.model.Player
import com.skycombat.game.model.factory.EnemyFactory
import com.skycombat.game.model.factory.LifePowerUpFactory
import com.skycombat.game.model.powerup.PowerUp
import com.skycombat.game.panel.FPSPanel
import com.skycombat.game.panel.GamePanel
import com.skycombat.game.panel.UPSPanel
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Represents the Game View
 * @param context : the context onto which the game will be drawn
 * @param MAX_WIDTH : the max width of the gameview
 * @param MAX_HEIGHT : the max height of the gameview
 */
class GameView(context: Context, private val MAX_WIDTH : Float,private val MAX_HEIGHT : Float ) : SurfaceView(context), SurfaceHolder.Callback {
    private val GAME_OVER_LISTENERS : ArrayList<GameOverListener> = ArrayList()
    private val ENEMY_FACTORY = EnemyFactory(this);
    private val LIFE_POWERUP_FACTORY = LifePowerUpFactory(this);

    private var enemies : CopyOnWriteArrayList<Enemy>    = CopyOnWriteArrayList();
    private var powerUps : CopyOnWriteArrayList<PowerUp> = CopyOnWriteArrayList();
    private var panels : CopyOnWriteArrayList<GamePanel> = CopyOnWriteArrayList();
    // TODO : rendere privata e gestirla a eventi
    public  val bullets : CopyOnWriteArrayList<Bullet>   = CopyOnWriteArrayList();

    var player : Player = Player(MAX_WIDTH / 2, MAX_HEIGHT / 5 * 4, 40F, this)
    private val startTime = System.currentTimeMillis();
    private var gameLoop: GameLoop = GameLoop(this, holder);

    init {
        holder.addCallback(this);
        focusable = View.FOCUSABLE;

        panels.add(FPSPanel(20F, MAX_HEIGHT/2, gameLoop, this ))
        panels.add(UPSPanel(20F, MAX_HEIGHT/2 + 100, gameLoop, this ))
    }

    override fun draw(canvas: Canvas?) {
        if (player.health <= 0) {
            return;
        }
        super.draw(canvas)
        player.draw(canvas)
        panels.forEach  { el -> el.draw(canvas) }
        enemies.forEach { el -> el.draw(canvas) }
        bullets.forEach { el -> el.draw(canvas) }
        powerUps.forEach{ el -> el.draw(canvas) }
    }
    private var eventEmitted = false;


    /**
     * Updates the view of the whole game
     * @see EnemyFactory
     * @see LifePowerUpFactory
     * @see GameOverListener
     * @see Bullet
     * @see Enemy
     * @see Player
     */
    fun update() {
        if (player.health <= 0) {
            if(!eventEmitted) {
                stop()
                GAME_OVER_LISTENERS.forEach { el ->
                    el.gameOver(System.currentTimeMillis() - startTime)
                }
            }
            eventEmitted = true;
            return;
        }

        enemies.removeIf(Enemy::isDead)
        powerUps.removeIf(PowerUp::isUsed)
        bullets.removeIf(Bullet::toRemove)

        if(enemies.size == 0){
            enemies .add(ENEMY_FACTORY.generate())
            powerUps.add(LIFE_POWERUP_FACTORY.generate())
        }
        player.update(bullets, powerUps)
        enemies.forEach{ el -> el.update(bullets) }
        bullets.forEach(Bullet::update)
        powerUps.forEach(PowerUp::update)
    }


    /**
     * Updates the view of the whole game
     * @see GameLoop
     */
    fun pause() : Unit {
        gameLoop.stopLoop();
    }
    /**
     * Updates the view of the whole game
     * @see GameLoop
     */
    fun stop() : Unit {
        gameLoop.killLoop();
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (gameLoop.state == Thread.State.TERMINATED) {
            val surfaceHolder = getHolder()
            surfaceHolder.addCallback(this)
            gameLoop = GameLoop(this, surfaceHolder)
        }

        gameLoop.startLoop()
    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                player.setPosition(event.x, player.positionY)
                true
            }
            else -> true;
        }
    }
    /**
     * Gets the maximum Game Height
     * @return MAX_HEIGHT
     */
    fun getMaxHeight() : Float {
        return MAX_HEIGHT
    }
    /**
     * Gets the maximum Game Width
     * @return MAX_WIDTH
     */
    fun getMaxWidth() : Float {
        return MAX_WIDTH
    }
    /**
     * Sets the gameoverlistener
     * @see GameOverListener
     */
    fun setGameOverListener(listener : GameOverListener) {
        GAME_OVER_LISTENERS.add(listener)
    }
}