package com.skycombat.game.model

import android.content.Context
import android.graphics.*
import android.util.Log
import com.skycombat.game.GameView
import com.skycombat.game.model.component.HealthBar
import java.util.*
import kotlin.math.pow
/**
 * Represents an Enemy
 * @param player : used as a base to draw the enemy
 * @param left : movement towards left
 * @param top : movement towards the top
 * @param width : enemy's width
 * @param height : enemy's height
 * @param scene : the gameview onto which the enemy will be drawn
 */
class Enemy(var player : Player, var left : Float, var top : Float, var width: Float, var height: Float , var scene : GameView) : HasHealth{
    var paint : Paint = Paint();
    companion object{
        val MAX_HEALTH : Float = 300f
        val SHOT_EVERY_UPDATES : Int = 100;
    }
    var curUpdatesFromShot = 0;
    var health : Float = MAX_HEALTH
    var healthBar : HealthBar;
    init {
        paint.color = Color.RED

        var hbColor : Paint = Paint()
        hbColor.setColor(Color.RED)
        healthBar = HealthBar(
                RectF(20f, 10f, scene.getMaxWidth().toFloat()-20, 50f),
                hbColor,
                this
        );
    }
    /**
     * Draws the player and enemy's healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     * @see HealthBar
     */
    fun draw(canvas: Canvas?) {
        canvas?.drawRect(RectF(left, top, left + width, top+height), paint)
        healthBar.draw(canvas)
    }
    /**
     * Update bullets to the enemy
     * @param bullets : the bullets the enemy has shot
     * @see Bullet
     * @see HealthBar
     */
    fun update(bullets : List<Bullet>) {

        curUpdatesFromShot++
        if(curUpdatesFromShot >= SHOT_EVERY_UPDATES){
            curUpdatesFromShot = 0;
            shoot();
        }


        var center : Float = left + width / 2;
        var dx : Float = 2f
        if(Math.abs(center - player.positionX) < dx ){
            left = player.positionX - width / 2
        } else if (center < player.positionX){
            left += dx
        } else {
            left -= dx
        }

        bullets.forEach{
            el -> run {
                if (el.y+ Bullet.RADIUS < top+height && el.y- Bullet.RADIUS > top && el.x > left && el.x < left + width ) {
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
     * Shoots the bullet in the right direction
     * @see Bullet
     */
    fun shoot() : Unit{
        scene.bullets.add(Bullet( left + width/2, top + height, Bullet.Direction.DOWN, scene))
    }
    /**
     * Checks if the enemy is dead
     * @see HealthBar
     */
    fun isDead() : Boolean{
        return this.health <= 0
    }

    override fun getCurrentHealth(): Float {
        return health
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

}