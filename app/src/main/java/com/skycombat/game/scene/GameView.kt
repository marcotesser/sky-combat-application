/**
 * Project:  EverBuilds
 * File:  GameView.kt
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
import android.graphics.Canvas
import android.view.*
import com.skycombat.game.model.factory.enemy.GeneralEnemyFactory
import com.skycombat.game.model.factory.enemy.SeedGeneralEnemyFactory
import com.skycombat.game.model.factory.powerup.GeneralPowerUpFactory
import com.skycombat.game.model.factory.powerup.SeedGeneralPowerUpFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.Drawable
import com.skycombat.game.model.gui.component.Background
import com.skycombat.game.model.gui.element.GUIElement
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.enemy.Enemy
import com.skycombat.game.model.gui.element.ghost.Ghost
import com.skycombat.game.model.gui.element.powerup.PowerUp
import com.skycombat.game.model.gui.panel.GamePanel
import java.util.concurrent.CopyOnWriteArrayList
import java.util.stream.Stream
import kotlin.random.Random

/**
 * Represents the Game View
 * @param context : the context onto which the game will be drawn
 */
class GameView(
        context: Context,
        private val displayDimension : DisplayDimension,
        private var player : Player,
        private var drawableVisitor: GameViewDrawableVisitor = GameViewDrawableVisitor(context),
        private var ghosts : CopyOnWriteArrayList<Ghost> = CopyOnWriteArrayList(),
        seed : Long = Random.nextLong(),
        val generalEnemyFactory: GeneralEnemyFactory = SeedGeneralEnemyFactory(seed, displayDimension),
        val generalPowerUpFactory: GeneralPowerUpFactory = SeedGeneralPowerUpFactory(seed, displayDimension),
        var enemies : CopyOnWriteArrayList<Enemy>    = CopyOnWriteArrayList(),
        var powerUps : CopyOnWriteArrayList<PowerUp> = CopyOnWriteArrayList(),
        val bullets : CopyOnWriteArrayList<Bullet>   = CopyOnWriteArrayList(),
        var panels : CopyOnWriteArrayList<GamePanel> = CopyOnWriteArrayList(),
        val background : Background = Background(displayDimension),
) : SurfaceView(context), SurfaceHolder.Callback {
    private val gameOverObservable : GameOverObservable = GameOverObservable()
    private val playerIsDeadObservable : GameOverObservable = GameOverObservable()

    private val startTime = System.currentTimeMillis()
    private var gameLoop: GameLoop = GameLoop(this, holder)

    /*private var panels : CopyOnWriteArrayList<GamePanel> = CopyOnWriteArrayList(listOf(
        FPSPanel(20F, displayDimension.height/2, gameLoop, this ),
        UPSPanel(20F, displayDimension.height/2 + 100, gameLoop, this )
    ))*/

    val deadEnemies: ArrayList<Enemy> = ArrayList()

    init {
        player.addOnShootListener { bullet -> bullets.add(bullet) }
        holder.addCallback(this)
        focusable = View.FOCUSABLE
    }

    /**
     * override di super.draw, disegna nel canvas i vari componenti
     * @param canvas : canvas su cui disegnare
     */
    override fun draw(canvas: Canvas?) {
        if (isGameOver()) {
            return
        }
        super.draw(canvas)

        if(canvas != null){
            background.draw(canvas, drawableVisitor)
            Stream.concat(
                Stream.of(player),
                Stream.of(panels, enemies, bullets, powerUps, ghosts).flatMap(
                    List<Drawable>::stream
                )
            ).forEach{el -> el.draw(canvas, drawableVisitor)}
        }
    }

    /**
     * Aggiorna i vari componenti e verifica se è gameover
     */
    fun update() {
        if(player.isDead()){
            playerIsDeadObservable.notify(getMillisFromStart())
        }
        if (isGameOver()) {
            gameLoop.killLoop()
            gameOverObservable.notify(getMillisFromStart())
        } else {
            deadEnemies.addAll(enemies.filter(Enemy::isDead))

            listOf(enemies, powerUps, bullets, ghosts).forEach { ar ->
                ar.removeIf(GUIElement::shouldRemove)
            }

            if (enemies.isEmpty()) {
                val enemy = generalEnemyFactory.generate()
                enemies.add(enemy)
                enemy.addOnShootListener { bullet -> bullets.add(bullet) }
                powerUps.add(generalPowerUpFactory.generate())
            }

            player.update()
            enemies.forEach(Enemy::update)
            bullets.forEach(Bullet::update)
            powerUps.forEach(PowerUp::update)
            ghosts.forEach(Ghost::update)

            this.handleCollisions()
        }
    }

    /**
     * gestisce la collisione tra i vari componenti
     */
    private fun handleCollisions(){
        powerUps.filter {
            el -> el.collide(player) && el.shouldApply(player)
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

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}
    override fun surfaceCreated(holder: SurfaceHolder) {
        if (gameLoop.state == Thread.State.TERMINATED) {
            val surfaceHolder = getHolder()
            surfaceHolder.addCallback(this)
            gameLoop = GameLoop(this, surfaceHolder)
        }

        gameLoop.startLoop()
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                player.aimedPositionX = event.x
                true
            }
            else -> true
        }
    }

    /**
     * aggiunge un listener al gameover
     * @see GameOverObserver
     */
    fun addGameOverListener(observer : GameOverObserver) {
        gameOverObservable.attach(observer)
    }
    /**
     * aggiunge un listener alla morte del player
     * @see GameOverObserver
     */
    fun addPlayerIsDeadListener(observer : GameOverObserver) {
        playerIsDeadObservable.attach(observer)
    }

    /**
     * ritorna i millisecondi passati dall'inizio
     * @return millisecondi intercorsi dall'inizio
     */
    private fun getMillisFromStart(): Long{
        return System.currentTimeMillis() - startTime
    }

    /**
     * getter per il player corrente
     * @return il player corrente
     */
    fun getPlayer() : Player{
        return player
    }

    /**
     * setter per gli opponenti
     * @param opponents : opponenti da considerare
     */
    fun setGhosts(opponents : List<Ghost>){
        this.ghosts = CopyOnWriteArrayList(opponents)
    }

    /**
     * getter per capire se è gameover
     * @return
     *      true : è game over
     *      false : non è game over
     */
    private fun isGameOver() : Boolean{
        return this.player.isDead() && ghosts.isEmpty()
    }


    /**
     * mette in pausa il gameloop
     * @see GameLoop
     */
    fun pause() {
        gameLoop.stopLoop()
    }
}