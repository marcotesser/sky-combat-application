package com.skycombat.game.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.skycombat.game.GameView
import com.skycombat.game.model.component.HealthBar

abstract class AbstEntity(var positionX: Float, var positionY: Float, var scene: GameView) {
    var paint : Paint = Paint()
    var curUpdatesFromShot = 0
    var health : Float
    abstract var shotEveryUpdates: Int
    abstract var healthBar : HealthBar
    abstract val MAX_HEALTH : Float

    init {
        health = MAX_HEALTH
    }

    protected fun updateShots(){
        curUpdatesFromShot++
        if(curUpdatesFromShot >= shotEveryUpdates){
            curUpdatesFromShot = 0
            shoot()
        }
    }

    fun setPosition(x: Float, y: Float) {

        if(x > getWidth()/2F && x < scene.getMaxWidth() - getWidth()/2F){
            positionX = x
        } else if (x < getWidth()/2F) {
            positionX = getWidth()/2F;
        } else {
            positionX = scene.getMaxWidth() - getWidth()/2F
        }
        positionY = y;      // TODO se si intende implementare il movimento verticale
    }

    @JvmName("setHealth1")
    fun addDeltaHealth(heal:Float){
        health+=heal;
        if(health > MAX_HEALTH) health=MAX_HEALTH;
    }
    @JvmName("getHealth1")
    fun getHealth():Float = health;

    abstract fun isMerging(_particle:AbstParticle):Boolean

    abstract fun getWidth(): Float
    abstract fun getHeight():Float

    abstract fun shoot()

    abstract fun draw(canvas: Canvas?)

    abstract fun update()


}