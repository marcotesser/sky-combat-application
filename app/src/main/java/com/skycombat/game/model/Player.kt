package com.skycombat.game.model

import android.content.Context
import android.graphics.*
import android.util.Log
import com.skycombat.game.GameView
import com.skycombat.game.model.component.HealthBar
import com.skycombat.game.model.powerup.PowerUp
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow
import kotlin.math.sqrt
/**
 * Represents an Player
 * @param positionX : position on the x axis
 * @param positionY : position on the y axis
 * @param radius : player's radius
 * @param scene : the gameview onto which the player will be drawn
 */
class Player(var positionX : Float, var positionY : Float, var radius : Float, var scene : GameView) : HasHealth {
    val paint : Paint = Paint();
    companion object{
        val MAX_HEALTH : Float = 1000f
        val SHOT_EVERY_UPDATES : Int = 60;
    }
    var curUpdatesFromShot = 0;

    var health : Float = MAX_HEALTH
    lateinit var healthBar : HealthBar ;
    init {
        paint.color = Color.GREEN
        var hbColor : Paint = Paint()
        hbColor.setColor(Color.GREEN)
        healthBar = HealthBar(
                RectF(20f, scene.getMaxHeight().toFloat() - 50, scene.getMaxWidth().toFloat()-20, scene.getMaxHeight().toFloat() - 10),
                hbColor,
                this
        );
    }
    /**
     * Draws the player and player's healthbar
     * @param canvas : the canvas onto which the player will be drawn
     * @see HealthBar
     */
    fun draw(canvas: Canvas?) {
        canvas?.drawCircle(positionX, positionY, radius, paint)
        healthBar.draw(canvas)
    }
    /**
     * Update bullets and powerups relative to the player
     * @param bullets : the bullets the player has shot
     * @param powerUps : the powerUps appearing on the screen
     * @see Bullet
     * @see HealthBar
     */
    fun update( bullets: List<Bullet>, powerUps: List<PowerUp>) {
        curUpdatesFromShot++
        if(curUpdatesFromShot >= SHOT_EVERY_UPDATES){
            curUpdatesFromShot = 0;
            shoot();
        }
        powerUps.forEach{
            el -> run {
                val dist = sqrt((
                            ((el.getX()?: 0f) - positionX).pow(2) +
                            ((el.getY()?: 0f) - positionY).pow(2)).toDouble()
                )
                if (dist < PowerUp.RADIUS + radius) {
                    el.apply(this)
                }
            }
        }

        bullets.forEach{
            el -> run {
                val dist = sqrt((
                            (el.x - positionX).pow(2) +
                                    (el.y - positionY).pow(2)).toDouble())
                if (dist < Bullet.RADIUS + radius) {
                    el.hit()
                    if(el.damage.toFloat() > this.health){
                        this.health = 0f;
                    }else {
                        this.health -= el.damage.toFloat()
                    }
                }
            }
        }
        healthBar.update()
    }
    /**
     * Sets the player position
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
     * Shoots the bullet in the right direction
     * @see Bullet
     */
    fun shoot() : Unit{
        scene.bullets.add(Bullet( positionX , positionY - radius - Bullet.RADIUS - 2, Bullet.Direction.UP, scene))
    }

    override fun getCurrentHealth(): Float {
        return health
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

}