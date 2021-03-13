package com.skycombat.game.model.gui.element

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.scene.ViewContext
import com.skycombat.game.model.geometry.Circle
import com.skycombat.game.model.geometry.Entity
import com.skycombat.game.model.geometry.Rectangle
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.component.HealthBar
import com.skycombat.game.model.gui.component.PlayerHealthBar
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.strategy.PlayerCollisionStrategy
import com.skycombat.game.model.gui.element.ghost.strategy.AimedPositionStrategy
import com.skycombat.game.model.gui.event.ShootObservable
import com.skycombat.game.model.gui.properties.AimToPositionX
import com.skycombat.game.model.gui.properties.CanShoot
import com.skycombat.game.model.gui.properties.HasHealth

/**
 * Represents an Player
 */
class Player(private val velocity : Float, val aimedPositionStrategy: AimedPositionStrategy) : HasHealth, Circle, GUIElement, CanShoot, AimToPositionX {

    companion object{
        const val MAX_HEALTH : Float = 500f
        const val RADIUS: Float = 70F
    }
    private var updatesFromEndShield: Long= 0
    override var health : Float = MAX_HEALTH
    private var playerImg : Bitmap
    private var playerShieldImg : Bitmap
    var deadAt : Long? = null


    var context: ViewContext = ViewContext.getInstance()


    private var positionY:Float = context.getHeightScreen()/ 5 * 4
    private var positionX:Float = context.getWidthScreen()/2F
    var aimedPositionX:Float = this.positionX;

    override var weapon: Weapon = Weapon(this, Weapon.BulletType.CLASSIC, PlayerCollisionStrategy(), Bullet.Direction.UP)
    override var shootObservable = ShootObservable()

    private var healthBar : HealthBar = PlayerHealthBar(this)

    init {
        playerImg= Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.player)), RADIUS.toInt()*2, RADIUS.toInt()*2,false)
        playerShieldImg= Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.playershield)), RADIUS.toInt()*2, RADIUS.toInt()*2,false)
        deadAt = null;
    }
    /**
     * Draws the player and player's health-bar
     * @param canvas : the canvas onto which the player will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        if(isAlive()) {
            if (hasShield()) {
                canvas?.drawBitmap(playerShieldImg, positionX - RADIUS / 2, positionY - RADIUS / 2, null)
            } else {
                canvas?.drawBitmap(playerImg, positionX - RADIUS / 2, positionY - RADIUS / 2, null)
            }
            healthBar.draw(canvas)
        }
    }

    override fun shouldRemove(): Boolean {
        return this.isDead()
    }

    fun applyShield(duration: Long){
        updatesFromEndShield = duration
    }
    private fun hasShield():Boolean{
        return updatesFromEndShield >0
    }

    override fun isDamageable():Boolean{
        return !hasShield()
    }
    override fun update() {
        setDeadAtIfDead(System.currentTimeMillis())
        if(isAlive()) {
            aimedPositionStrategy.move(this)
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
        positionX = if(x > RADIUS && x < context.getWidthScreen() - RADIUS){
            x
        } else if (x < RADIUS) {
            RADIUS
        } else {
            context.getWidthScreen() - RADIUS
        }
        positionY = y
    }
    /**
     * Shoots the bullet in the right direction
     * @see Bullet
     */

    fun setBulletType(bulletType: Weapon.BulletType){
        weapon.setBulletType(bulletType)
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

    override fun aimToPos(): Float {
        return this.aimedPositionX
    }

    override fun velocity(): Float {
        return this.velocity
    }

    override fun collide(el: Rectangle): Boolean {
        if(isDead()) return false;
        return super.collide(el)
    }

    override fun collide(el: Circle): Boolean {
        if(isDead()) return false;
        return super.collide(el)
    }

    override fun collide(el: Entity): Boolean {
        if(isDead()) return false;
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

    fun setDeadAtIfDead(time : Long){
        if(this.health <= 0 && this.deadAt == null){
            this.deadAt = time
        }
    }
}