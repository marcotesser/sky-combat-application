package com.skycombat.game.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.skycombat.game.GameView


abstract class AbstParticle(var positionX:Float, var positionY: Float, val RADIUS:Float, val SPEED:Float, var scene: GameView, val DIRECTION: Direction=Direction.DOWN, val TARGET:Target = Target.PLAYER) {

    enum class Direction {
        UP, DOWN
    }
    enum class Target {
        ENEMIES, PLAYER
    }

    private var isHit:Boolean =false
    protected fun setHit(){
        isHit=true;
    }
    /**
     * Sets isHit to true
     */
    fun hit() : Unit{
        isHit = true
    }

    /**
     * Updates the bullet's direction
     */
    fun update() {
        positionY += when (DIRECTION) {
            Direction.UP -> -SPEED
            Direction.DOWN -> SPEED
        }
    }

    abstract fun draw(canvas: Canvas?)

    abstract fun handleHit(ent: AbstEntity)

    fun toRemove() : Boolean{
        return isHit || positionY < 0 || positionY > scene.getMaxHeight()
    }

    @JvmName("getPositionX1")
    fun getPositionX():Float = positionX
    @JvmName("getPositionY1")
    fun getPositionY():Float = positionY
    fun getRadius():Float = RADIUS
}