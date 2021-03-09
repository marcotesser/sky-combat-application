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
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.*
import com.skycombat.R
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.enemy.Enemy
import com.skycombat.game.model.Player
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.event.ShootListener
import com.skycombat.game.model.factory.EnemyFactory
import com.skycombat.game.model.factory.PowerUpFactory
import com.skycombat.game.model.powerup.PowerUp
import com.skycombat.game.model.support.Drawable
import com.skycombat.game.model.support.GUIElement
import com.skycombat.game.panel.FPSPanel
import com.skycombat.game.panel.GamePanel
import com.skycombat.game.panel.UPSPanel
import java.util.concurrent.CopyOnWriteArrayList
import java.util.stream.Stream

/**
 * Represents the Game View
 * @param context : the context onto which the game will be drawn
 * @param MAX_WIDTH : the max width of the gameview
 * @param MAX_HEIGHT : the max height of the gameview
 */
class GameView(context: Context, private val MAX_WIDTH : Float,private val MAX_HEIGHT : Float ) : SurfaceView(context), SurfaceHolder.Callback {
    private val GAME_OVER_LISTENERS : ArrayList<GameOverListener> = ArrayList()

    private var enemies : CopyOnWriteArrayList<Enemy>    = CopyOnWriteArrayList();
    private var powerUps : CopyOnWriteArrayList<PowerUp> = CopyOnWriteArrayList();
    private var panels : CopyOnWriteArrayList<GamePanel> = CopyOnWriteArrayList();
    // TODO : rendere privata e gestirla a eventi
    public val bullets : CopyOnWriteArrayList<Bullet>   = CopyOnWriteArrayList();
    var player : Player

    private val ENEMY_FACTORY: EnemyFactory
    private val POWERUP_FACTORY: PowerUpFactory

    private val startTime = System.currentTimeMillis();
    private var gameLoop: GameLoop = GameLoop(this, holder);

    var islandsImg: Bitmap
    var cloudsImg: Bitmap
    var backY : Float= 0f
    var backY2 : Float= 0f

    init {
        ViewContext.setContext(MAX_WIDTH, MAX_HEIGHT, resources);
        player = Player()
        ENEMY_FACTORY = EnemyFactory(100000);
        POWERUP_FACTORY = PowerUpFactory(100000); // TODO(Fake SEED to implement)

        player.addOnShootListener(object :ShootListener{
            override fun onShoot(bullet: Bullet) {
                bullets.add(bullet)
            }
        })
        holder.addCallback(this);
        focusable = View.FOCUSABLE;
        panels.add(FPSPanel(20F, MAX_HEIGHT/2, gameLoop, this ))
        panels.add(UPSPanel(20F, MAX_HEIGHT/2 + 100, gameLoop, this ))

        islandsImg= Bitmap.createScaledBitmap((BitmapFactory.decodeResource(resources, R.drawable.islands)),MAX_WIDTH.toInt(),MAX_WIDTH.toInt()*3,false)
        cloudsImg= Bitmap.createScaledBitmap((BitmapFactory.decodeResource(resources, R.drawable.clouds)),MAX_WIDTH.toInt(),MAX_WIDTH.toInt()*3,false)
    }

    override fun draw(canvas: Canvas?) {
        if (player.getCurrentHealth() <= 0) {
            return;
        }
        super.draw(canvas)
        //draw dello sfondo
        canvas?.drawBitmap(islandsImg, 0f, backY ,null)
        canvas?.drawBitmap(islandsImg, 0f, backY-MAX_WIDTH.toInt()*3 ,null)
        canvas?.drawBitmap(cloudsImg, 0f, backY2 ,null)
        canvas?.drawBitmap(cloudsImg, 0f, (backY2-MAX_WIDTH.toInt()*3) ,null)

        if(canvas != null){
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
            return;
        }
        listOf(enemies, powerUps, bullets).forEach{
            ar -> ar.removeIf(GUIElement::shouldRemove)
        }

        if(enemies.size == 0){
            var enemy=ENEMY_FACTORY.generate()
            enemies.add(enemy)
            enemy.addOnShootListener(object : ShootListener{
                override fun onShoot(bullet: Bullet) {
                    bullets.add(bullet)
                }

            })
            powerUps.add(POWERUP_FACTORY.generate())
        }

        handleCollisions()

        player.update()
        enemies.forEach(Enemy::update)
        bullets.forEach(Bullet::update)
        powerUps.forEach(PowerUp::update)

        //movimento sfondo
        if(backY<MAX_WIDTH.toInt()*3) {
            backY += 8
        }else{
            backY=0f
        }
        if(backY2<MAX_WIDTH.toInt()*3) {
            backY2 += 12
        }else{
            backY2=0f
        }
    }

    private fun getCurrentTimeFromStart(): Long{
        return System.currentTimeMillis() - startTime
    }

    private fun handleCollisions(){
        powerUps.filter { el -> el.collide(player)
        }.forEach{ el -> el.applyPowerUPEffects(player)
        }

        Stream.concat(enemies.stream(), Stream.of(player)).forEach{
                entity ->
            bullets.filter { el -> entity.collide(el)
            }.forEach{ el -> el.applyCollisionEffects(entity)
            }

        }
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
     * Sets the gameoverlistener
     * @see GameOverListener
     */
    fun setGameOverListener(listener : GameOverListener) {
        GAME_OVER_LISTENERS.add(listener)
    }
}