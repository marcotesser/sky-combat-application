package com.skycombat.game.model

import android.graphics.*
import com.skycombat.game.GameView
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.component.HealthBar
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
class Player(var positionX : Float, var positionY : Float, private var radius : Float, var scene : GameView) : HasHealth, Circle, GUIElement {
    val paint : Paint = Paint();
    companion object{
        val MAX_HEALTH : Float = 1000f
        val SHOT_EVERY_UPDATES : Int = 60;
    }
    var curUpdatesFromShot = 0;

    private var health : Float = MAX_HEALTH
    var healthBar : HealthBar ;
    init {
        paint.color = Color.GREEN
        val hbColor = Paint()
        hbColor.color = Color.GREEN
        healthBar = HealthBar(
                RectF(20f, scene.getMaxHeight() - 50, scene.getMaxWidth()-20, scene.getMaxHeight() - 10),
                hbColor,
                this
        );
    }
    /**
     * Draws the player and player's healthbar
     * @param canvas : the canvas onto which the player will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(positionX, positionY, radius, paint)
        healthBar.draw(canvas)
    }

    override fun shouldRemove(): Boolean {
        return this.isDead()
    }

    fun isDead(): Boolean{
        return this.health <= 0
    }

    /**
     * Update bullets and powerups relative to the player
     * @param bullets : the bullets the player has shot
     * @param powerUps : the powerUps appearing on the screen
     * @see Bullet
     * @see HealthBar
     */
    override fun update() {
        curUpdatesFromShot++
        if(curUpdatesFromShot >= SHOT_EVERY_UPDATES){
            curUpdatesFromShot = 0;
            shoot();
        }

        healthBar.update()
    }


    /**
     * Sets the player position
     * @param x : player's X position
     * @param y : player's Y position
     */
    fun setPosition(x: Float, y: Float) {
        if(x > radius && x < scene.getMaxWidth() - radius){
            positionX = x
        } else if (x < radius) {
            positionX = radius;
        } else {
            positionX = scene.getMaxWidth() - radius
        }
        positionY = y;
    }
    /**
     * Shoots the bullet in the right direction
     * @see Bullet
     */
    fun shoot() {
        scene.bullets.add(Bullet( positionX , positionY - radius - Bullet.RADIUS - 2, Bullet.Direction.UP, scene))
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
        return this.radius
    }

}