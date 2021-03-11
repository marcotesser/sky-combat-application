package com.skycombat.game.model.gui.element.powerup

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import com.skycombat.game.scene.ViewContext
import com.skycombat.game.model.geometry.Circle
import com.skycombat.game.model.gui.element.GUIElement
import com.skycombat.game.model.gui.element.Player

abstract class PowerUp(var x : Float, var y : Float) : Circle, GUIElement {

    companion object {
        const val RADIUS: Float = 50f
        const val SPEED: Float = 8F
    }

    abstract var powerUpImg : Bitmap
    val context : ViewContext = ViewContext.getInstance()
    var used : Boolean = false

    override fun update(){
        this.y += SPEED
    }

    override fun draw(canvas : Canvas?){
        canvas?.drawBitmap(powerUpImg,x - getRadius(),y - getRadius(),null)
    }

    override fun shouldRemove(): Boolean {
        return this.used || this.y > context.getHeightScreen()
    }

    override fun getCenter(): PointF {
        return PointF(this.x, this.y)
    }
    override fun getRadius(): Float {
        return RADIUS
    }

    abstract fun applyPowerUPEffects(player: Player)
}