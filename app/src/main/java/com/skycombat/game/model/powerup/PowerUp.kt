package com.skycombat.game.model.powerup

import android.graphics.Canvas
import com.skycombat.game.model.Player

interface PowerUp {
    companion object {
        public val RADIUS: Float = 20f;
    }
    fun apply(player : Player)
    fun getX() : Float?
    fun getY() : Float?
    fun isUsed() : Boolean
    fun update() : Unit
    fun draw(canvas: Canvas?) : Unit
}