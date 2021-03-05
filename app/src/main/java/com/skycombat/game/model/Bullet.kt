package com.skycombat.game.model

import android.content.Context
import android.graphics.*
import android.util.Log
import com.skycombat.game.GameView
import com.skycombat.game.model.component.HealthBar
/**
 * Represents a bullet
 * @param x : axis x coordinate of the bullet
 * @param y : axis y coordinate of the bullet
 * @param direction : direction of the bullet
 * @param scene : the gameview onto which the bullet will be drawn
 */
class Bullet(var x : Float, var y : Float, var direction : Direction, var scene : GameView) {
    enum class Direction {
        UP, DOWN
    }
    public var damage = 1000;
    private var isHit : Boolean = false;
    var paint : Paint = Paint();
    private val DY : Float = 10.0f
    companion object {
        var RADIUS: Float = 10.0f
    }
    init {
        paint.color = if( direction == Bullet.Direction.DOWN) Color.RED else Color.GREEN
    }
    /**
     * Draws the bullet
     * @param canvas : the canvas onto which the bullet will be drawn
     */
    fun draw(canvas: Canvas?) {
        canvas?.drawCircle(x, y , RADIUS , paint)
    }
    /**
     * Updates the bullet's direction
     */
    fun update() {
        y += when (direction) {
            Direction.UP -> -DY
            Direction.DOWN -> DY
        }
    }
    /**
     * Sets isHit to true
     */
    fun hit() : Unit{
        isHit = true
    }
    /**
     * Determines if something has been hit
     * @return isHit
     */
    fun isHit() : Boolean{
        return isHit
    }
    /**
     * Removes if hit gets object out of context
     * @return isHit || this.y < 0 || this.y > scene.getMaxHeight()
     */
    fun toRemove() : Boolean{
        return isHit || this.y < 0 || this.y > scene.getMaxHeight()
    }

}