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
package com.skycombat.game.scene


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.*
import com.skycombat.R
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.enemy.Enemy
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.gui.event.ShootListener
import com.skycombat.game.model.factory.EnemyFactory
import com.skycombat.game.model.factory.PowerUpFactory
import com.skycombat.game.model.gui.element.powerup.PowerUp
import com.skycombat.game.model.gui.Drawable
import com.skycombat.game.model.gui.component.Background
import com.skycombat.game.model.gui.element.GUIElement
import com.skycombat.game.model.gui.panel.FPSPanel
import com.skycombat.game.model.gui.panel.GamePanel
import com.skycombat.game.model.gui.panel.UPSPanel
import java.util.concurrent.CopyOnWriteArrayList
import java.util.stream.Stream

/**
 * Represents the Game View
 * @param context : the context onto which the game will be drawn
 */
class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private val GAME_OVER_LISTENERS : ArrayList<GameOverListener> = ArrayList()


    private val startTime = System.currentTimeMillis()
    private var gameLoop: GameLoop = GameLoop(this, holder)
    private val viewContext = ViewContext.getInstance()
    private var enemies : CopyOnWriteArrayList<Enemy>    = CopyOnWriteArrayList()
    private var powerUps : CopyOnWriteArrayList<PowerUp> = CopyOnWriteArrayList()
    private val bullets : CopyOnWriteArrayList<Bullet>   = CopyOnWriteArrayList()
    private var player : Player = Player()
    private var panels : CopyOnWriteArrayList<GamePanel> = CopyOnWriteArrayList(listOf(
            FPSPanel(20F, viewContext.height/2, gameLoop, this ),
            UPSPanel(20F, viewContext.height/2 + 100, gameLoop, this )
    ))
    private val background : Background = Background(
            Bitmap.createScaledBitmap((BitmapFactory.decodeResource(resources, R.drawable.islands)),viewContext.width.toInt(),viewContext.width.toInt()*3,false),
            Bitmap.createScaledBitmap((BitmapFactory.decodeResource(resources, R.drawable.clouds)),viewContext.width.toInt(),viewContext.width.toInt()*3,false)
    )

    private val ENEMY_FACTORY: EnemyFactory = EnemyFactory(100000) // TODO SEED
    private val POWERUP_FACTORY: PowerUpFactory = PowerUpFactory(100000) // TODO SEED



    init {


        player.addOnShootListener(object :ShootListener{
            override fun onShoot(bullet: Bullet) {
                bullets.add(bullet)
            }
        })
        holder.addCallback(this)
        focusable = View.FOCUSABLE
    }

    override fun draw(canvas: Canvas?) {
        if (player.getCurrentHealth() <= 0) {
            return
        }
        super.draw(canvas)

        if(canvas != null){
            background.draw(canvas)
            Stream.concat(
                Stream.of(player),
                Stream.of(panels, enemies, bullets, powerUps).flatMap(
                    List<Drawable>::stream
                )
            ).forEach{el -> el.draw(canvas)}
        }
    }


    /**
     * Updates the view of the whole game
     * @see EnemyFactory
     * @see PowerUpFactory
     * @see GameOverListener
     * @see Bullet
     * @see Enemy
     * @see Player
     */
    fun update() {
        if (player.isDead()) {
            stop()
            GAME_OVER_LISTENERS.forEach { el ->
                el.gameOver(getCurrentTimeFromStart())
            }
            return
        }
        listOf(enemies, powerUps, bullets).forEach{
            ar -> ar.removeIf(GUIElement::shouldRemove)
        }

        if(enemies.size == 0){
            val enemy = ENEMY_FACTORY.generate()
            enemies.add(enemy)
            enemy.addOnShootListener(object : ShootListener{
                override fun onShoot(bullet: Bullet) {
                    bullets.add(bullet)
                }
            })
            powerUps.add(POWERUP_FACTORY.generate())
        }

        this.handleCollisions()

        player.update()
        enemies.forEach(Enemy::update)
        bullets.forEach(Bullet::update)
        powerUps.forEach(PowerUp::update)


    }

    private fun getCurrentTimeFromStart(): Long{
        return System.currentTimeMillis() - startTime
    }

    private fun handleCollisions(){
        powerUps.filter {
            el -> el.collide(player)
        }.forEach{
            el -> el.applyPowerUPEffects(player)
        }

        Stream.concat(enemies.stream(), Stream.of(player)).forEach{
            entity ->
                bullets.filter {
                    el -> entity.collide(el)
                }.forEach{
                    el -> el.applyCollisionEffects(entity)
                }
        }
    }


    /**
     * Updates the view of the whole game
     * @see GameLoop
     */
    fun pause() {
        gameLoop.stopLoop()
    }
    /**
     * Updates the view of the whole game
     * @see GameLoop
     */
    fun stop() {
        gameLoop.killLoop()
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
            else -> true
        }
    }
    /**
     * Sets the gameoverlistener
     * @see GameOverListener
     */
    fun setGameOverListener(listener : GameOverListener) {
        GAME_OVER_LISTENERS.add(listener)
    }
}