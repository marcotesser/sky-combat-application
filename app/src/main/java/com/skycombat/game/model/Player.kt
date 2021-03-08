package com.skycombat.game.model

import android.graphics.*
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.bullet.strategy.PlayerCollisionStrategy
import com.skycombat.game.model.component.HealthBar
import com.skycombat.game.model.component.PlayerHealthBar
import com.skycombat.game.model.event.ShootObserver
import com.skycombat.game.model.event.ShootListener
import com.skycombat.game.model.support.CanShoot
import com.skycombat.game.model.support.Circle
import com.skycombat.game.model.support.GUIElement
import java.util.*

/**
 * Represents an Player
 * @param positionX : position on the x axis
 * @param positionY : position on the y axis
 * @param radius : player's radius
 * @param scene : the gameview onto which the player will be drawn
 */
class Player() : HasHealth, Circle, GUIElement, CanShoot {

    val paint : Paint = Paint();
    companion object{
        val MAX_HEALTH : Float = 1000f
        val RADIUS: Float = 20F;
    }
    var updatesFromEndShield: Long= 0
    private var health : Float = MAX_HEALTH
    var positionX:Float
    var positionY:Float

    var healthBar : HealthBar;
    var context: ViewContext = ViewContext.getInstance()

    override var weapon:Weapon = Weapon(this, Weapon.BulletType.CLASSIC, PlayerCollisionStrategy())
    override var shootObserver = ShootObserver()

    init {
        positionX=context.getWidthScreen()/2F
        positionY= context.getHeightScreen()/ 5 * 4
        paint.color = Color.GREEN
        healthBar = PlayerHealthBar(this);
    }
    /**
     * Draws the player and player's healthbar
     * @param canvas : the canvas onto which the player will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(positionX, positionY, RADIUS, paint)
        healthBar.draw(canvas)
    }

    override fun shouldRemove(): Boolean {
        return this.isDead()
    }

    fun isDead(): Boolean{
        return this.health <= 0
    }

    fun applyShield(duration: Long){
        updatesFromEndShield = duration
    }
    fun hasShield():Boolean{
        return updatesFromEndShield >0
    }

    override fun isDamageable():Boolean{
        return !hasShield();
    }

    override fun update() {

        weapon.update()

        if(updatesFromEndShield >0) updatesFromEndShield --

        healthBar.update()
    }


    /**
     * Sets the player position
     * @param x : player's X position
     * @param y : player's Y position
     */
    fun setPosition(x: Float, y: Float) {
        if(x > RADIUS && x < context.getWidthScreen() - RADIUS){
            positionX = x
        } else if (x < RADIUS) {
            positionX = RADIUS;
        } else {
            positionX = context.getWidthScreen() - RADIUS
        }
        positionY = y;
    }
    /**
     * Shoots the bullet in the right direction
     * @see Bullet
     */

    fun setBulletType(bulletType: Weapon.BulletType){
        weapon.setBulletType(bulletType)
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

    override fun getCenter(): PointF {
        return PointF(this.positionX, this.positionY)
    }

    override fun getRadius(): Float {
        return RADIUS
    }

    override fun startPointOfShoot(): PointF {
        return PointF(positionX, positionY - RADIUS - 2F)
    }

}