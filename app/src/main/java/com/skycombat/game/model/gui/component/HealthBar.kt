package com.skycombat.game.model.gui.component

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.skycombat.game.model.gui.DrawVisitor

/**
 * Represents the healthbar
 */
abstract class HealthBar : GUIComponent {

    lateinit var life: RectF
    var percentage:Float =1F

    /**
     * Draws the healthbar
     */
    abstract fun update()
    override fun draw(canvas: Canvas?, visitor: DrawVisitor) {
        this.update()
        visitor.draw(canvas, this)
    }

}