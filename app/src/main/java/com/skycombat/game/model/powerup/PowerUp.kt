package com.skycombat.game.model.powerup

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import com.skycombat.game.model.Player
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.support.Circle
import com.skycombat.game.model.support.GUIElement

abstract class PowerUp(var x : Float, var y : Float, var speed:Float)
    : Circle, GUIElement {

    companion object {
        val RADIUS: Float = 50f;
    }

    abstract var powerUpImg: Bitmap
    val context: ViewContext = ViewContext.getInstance()
    var used : Boolean = false

    override fun update(){
        this.y += speed
    }

    override fun draw(canvas : Canvas?){
        canvas?.drawBitmap(powerUpImg,x-getRadius(),y-getRadius(),null)
    }

    override fun shouldRemove(): Boolean {
        return this.used || this.y < 0 || this.y > context.getHeightScreen()
    }

    override fun getCenter(): PointF {
        return PointF(this.x, this.y)
    }
    override fun getRadius(): Float {
        return RADIUS
    }

    abstract fun applyPowerUPEffects(player: Player)
}