package com.skycombat.game.model

import android.graphics.*
import com.skycombat.game.GameView
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.bullet.strategy.EnemyCollisionStrategy
import com.skycombat.game.model.component.HealthBar
import com.skycombat.game.model.event.ShootObserver
import com.skycombat.game.model.event.ShootListener
import com.skycombat.game.model.support.GUIElement
import com.skycombat.game.model.support.Rectangle
import java.util.*
import kotlin.math.abs

/**
 * Represents an Enemy
 * @param player : used as a base to draw the enemy
 * @param left : movement towards left
 * @param top : movement towards the top
 * @param width : enemy's width
 * @param height : enemy's height
 * @param scene : the gameview onto which the enemy will be drawn
 */
class Enemy(var player : Player, var left : Float, var top : Float, var width: Float, var height: Float , var scene : GameView) : HasHealth, Rectangle, GUIElement{
    var paint : Paint = Paint();
    companion object{
        val MAX_HEALTH : Float = 3000f
        val SHOT_EVERY_UPDATES : Int = 100;
    }
    var weapon: Weapon = Weapon(this, Weapon.BulletType.CLASSIC, EnemyCollisionStrategy())
    var shootObserver = ShootObserver()
    var context: ViewContext = ViewContext.getInstance()
    var curUpdatesFromShot = 0;
    private var health : Float = MAX_HEALTH
    var healthBar : HealthBar;
    init {
        paint.color = Color.RED

        val hbColor = Paint()
        hbColor.color = Color.RED
        healthBar = HealthBar(
                RectF(20f, 10f, context.getWidthScreen()-20, 50f),
                hbColor,
                this
        );
    }
    /**
     * Draws the player and enemy's healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(RectF(left, top, left + width, top+height), paint)
        healthBar.draw(canvas)
    }
    /**
     * Update bullets to the enemy
     * @param bullets : the bullets the enemy has shot
     * @see Bullet
     * @see HealthBar
     */
    override fun update() {

        curUpdatesFromShot++
        if(curUpdatesFromShot >= SHOT_EVERY_UPDATES){
            curUpdatesFromShot = 0;
            shoot();
        }


        val center : Float = left + width / 2;
        val dx = 2f
        when {
            abs(center - player.positionX) < dx -> left = player.positionX - width / 2
            center < player.positionX -> left += dx
            else -> left -= dx
        }

        healthBar.update()
    }

    override fun shouldRemove(): Boolean {
        return this.isDead()
    }

    fun addOnShootListener(shootListener: ShootListener){
        shootObserver.attach(shootListener);
    }

    /**
     * Shoots the bullet in the right direction
     * @see Bullet
     */
    fun shoot() {
        shootObserver.notify(weapon.generateBullet())
    }
    /**
     * Checks if the enemy is dead
     * @see HealthBar
     */
    fun isDead() : Boolean{
        return this.health <= 0
    }

    override fun getCurrentHealth(): Float {
        return health
    }

    override fun setHealth(health: Float) {
        this.health = health
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

    override fun getPosition(): RectF {
        return RectF(left, this.top, this.left + this.width , this.top + this.height)
    }
}