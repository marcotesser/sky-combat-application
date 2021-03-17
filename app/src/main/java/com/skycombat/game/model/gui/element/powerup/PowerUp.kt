package com.skycombat.game.model.gui.element.powerup

import android.graphics.Canvas
import android.graphics.PointF
import com.skycombat.game.model.geometry.Circle
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.DrawVisitor
import com.skycombat.game.model.gui.element.GUIElement
import com.skycombat.game.model.gui.element.Player

abstract class PowerUp(var x : Float, var y : Float, val displayDimension : DisplayDimension) : Circle, GUIElement {

    companion object {
        const val RADIUS: Float = 50f
        const val SPEED: Float = 8F
    }

    abstract var powerUpImg : Int
    var used : Boolean = false

    fun shouldApply(player: Player) : Boolean{
        return player.isAlive()
    }
    override fun update(){
        this.y += SPEED
    }

    override fun draw(canvas: Canvas?, visitor: DrawVisitor) {
        visitor.draw(canvas, this)
    }

    override fun shouldRemove(): Boolean {
        return this.used || this.y > displayDimension.height
    }

    override fun getCenter(): PointF {
        return PointF(this.x, this.y)
    }
    override fun getRadius(): Float {
        return RADIUS
    }

    abstract fun applyPowerUPEffects(player: Player)
}