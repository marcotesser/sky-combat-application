package com.skycombat.game.model.enemy

import android.graphics.*
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.Weapon
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.bullet.strategy.EnemyCollisionStrategy
import com.skycombat.game.model.bullet.strategy.PlayerCollisionStrategy
import com.skycombat.game.model.component.EnemyHealthBar
import com.skycombat.game.model.component.HealthBar
import com.skycombat.game.model.event.ShootObserver
import com.skycombat.game.model.event.ShootListener
import com.skycombat.game.model.support.GUIElement
import com.skycombat.game.model.support.Rectangle
import java.util.*

/**
 * Represents an Enemy
 * @param player : used as a base to draw the enemy
 * @param left : movement towards left
 * @param top : movement towards the top
 * @param width : enemy's width
 * @param height : enemy's height
 * @param scene : the gameview onto which the enemy will be drawn
 */
abstract class Enemy(var left : Float, var top : Float, bulletType: Weapon.BulletType) : HasHealth, Rectangle, GUIElement{
    var paint : Paint = Paint();

    var healthBar : HealthBar;
    var context: ViewContext = ViewContext.getInstance()
    var shootObserver = ShootObserver()
    var weapon:Weapon = Weapon(this, bulletType, EnemyCollisionStrategy())

    private var health : Float
    var verticalAttitude: Int =1
    var horizontalAttitude: Int =1

    init {
        paint.color = Color.RED
        healthBar = EnemyHealthBar(this);
        health=getMaxHealth()
    }
    /**
     * Draws the player and enemy's healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(RectF(left, top, left + getWidth(), top+getHeight()), paint)
        healthBar.draw(canvas)
    }
    /**
     * Update bullets to the enemy
     * @param bullets : the bullets the enemy has shot
     * @see Bullet
     * @see HealthBar
     */
    override fun update() {

        MovHandle()

        healthBar.update()
    }
    /*
    val center : Float = left + width / 2;
    val dx = 2f
    when {
        abs(center - positionPlayer.x) < dx -> left = positionPlayer.x- width / 2
        center < positionPlayer.x -> left += dx
        else -> left -= dx
    }
     */

    abstract fun MovHandle()

    abstract fun getWidth():Float

    abstract fun getHeight():Float


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

    override fun getPosition(): RectF {
        return RectF(left, this.top, this.left + getWidth() , this.top + getHeight())
    }
}