package com.skycombat.game.model.gui.element

import android.graphics.Canvas
import android.graphics.PointF
import com.skycombat.R
import com.skycombat.game.model.factory.bullet.BulletFactory
import com.skycombat.game.model.factory.bullet.ClassicBulletFactory
import com.skycombat.game.model.geometry.Circle
import com.skycombat.game.model.geometry.Entity
import com.skycombat.game.model.geometry.Rectangle
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.DrawVisitor
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.component.HealthBar
import com.skycombat.game.model.gui.component.PlayerHealthBar
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.collision.PlayerCollisionStrategy
import com.skycombat.game.model.gui.element.ghost.movement.MovementStrategy
import com.skycombat.game.model.gui.event.PlayerDeathObservable
import com.skycombat.game.model.gui.event.PlayerDeathObserver
import com.skycombat.game.model.gui.event.ShootObservable
import com.skycombat.game.model.gui.properties.AimToPositionX
import com.skycombat.game.model.gui.properties.CanShoot
import com.skycombat.game.model.gui.properties.HasHealth

/**
 * Represents an Player
 */
class Player(private val velocity : Float, val movementStrategy: MovementStrategy, val displayDimension: DisplayDimension) : HasHealth, Circle, GUIElement, CanShoot, AimToPositionX {

    companion object{
        const val MAX_HEALTH : Float = 500f
        const val RADIUS: Float = 70F
    }
    private var updatesFromEndShield: Long= 0
    override var health : Float = MAX_HEALTH
    var playerImg : Int = R.drawable.player
    private var startTime : Long = System.currentTimeMillis()
    var playerShieldImg : Int = R.drawable.playershield
    private var deathObservable : PlayerDeathObservable = PlayerDeathObservable()
    var deadAt : Long? = null


    private var positionY:Float = displayDimension.height/ 5 * 4
    private var positionX:Float = displayDimension.width/2F
    var aimedPositionX:Float = this.positionX

    override var weapon: Weapon = Weapon(this, ClassicBulletFactory() , PlayerCollisionStrategy(), Bullet.Direction.UP, displayDimension)
    override var shootObservable = ShootObservable()

    var healthBar : HealthBar = PlayerHealthBar(this, displayDimension)

    /**
     * Draws the player and player's health-bar
     * @param canvas : the canvas onto which the player will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?, visitor: DrawVisitor) {

        if(this.isAlive()) {
            visitor.draw(canvas, this)
            this.healthBar.draw(canvas, visitor)
        }
    }

    override fun shouldRemove(): Boolean {
        return this.isDead()
    }

    fun applyShield(duration: Long){
        updatesFromEndShield = duration
    }
    fun hasShield():Boolean{
        return updatesFromEndShield >0
    }

    override fun isDamageable():Boolean{
        return !hasShield()
    }
    override fun update() {
        setDeadAtIfDead(System.currentTimeMillis())
        if(isAlive()) {
            movementStrategy.move(this)
            weapon.update()
            if (updatesFromEndShield > 0) updatesFromEndShield--
        }
    }

    /**
     * Sets the player position
     * @param x : player's X position
     * @param y : player's Y position
     */
    fun setPosition(x: Float, y: Float) {
        positionX = if(x > RADIUS && x < displayDimension.width - RADIUS){
            x
        } else if (x < RADIUS) {
            RADIUS
        } else {
            displayDimension.width - RADIUS
        }
        positionY = y
    }
    /**
     * Shoots the bullet in the right direction
     * @see Bullet
     */

    fun setBulletType(bulletFactory: BulletFactory){
        weapon.setBulletType(bulletFactory)
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

    override fun setX(pos: Float) {
        this.positionX = pos
    }

    override fun getX(): Float {
        return this.positionX
    }
    fun getY(): Float {
        return this.positionY
    }

    override fun aimToPos(): Float {
        return this.aimedPositionX
    }

    override fun velocity(): Float {
        return this.velocity
    }

    override fun collide(el: Rectangle): Boolean {
        if(isDead()) return false
        return super.collide(el)
    }

    override fun collide(el: Circle): Boolean {
        if(isDead()) return false
        return super.collide(el)
    }

    override fun collide(el: Entity): Boolean {
        if(isDead()) return false
        return super.collide(el)
    }

    override fun isDead(): Boolean {
        setDeadAtIfDead(System.currentTimeMillis())
        return super.isDead()
    }

    override fun isAlive(): Boolean {
        setDeadAtIfDead(System.currentTimeMillis())
        return super.isAlive()
    }

    private fun setDeadAtIfDead(time : Long){
        if(this.health <= 0 && this.deadAt == null){
            this.deadAt = time
            deathObservable.notify(time)
        }
    }
    fun addOnDeathOccurListener(listener: PlayerDeathObserver){
        deathObservable.attach(listener)
    }
    fun aliveFor() : Long?{
        return if(deadAt != null) deadAt?.minus(startTime)
        else null
    }
}