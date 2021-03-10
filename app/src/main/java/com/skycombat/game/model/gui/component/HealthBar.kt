package com.skycombat.game.model.gui.component

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

/**
 * Represents the healthbar
 */
abstract class HealthBar : GUIComponent {

    private var paint = Paint()
    lateinit var life: RectF
    var percentage:Float =1F

    /**
     * Draws the healthbar
     */
    abstract fun update()
    override fun draw(canvas: Canvas?) {
        this.update()
        paint.color = Color.rgb(
            if(percentage<0.5F) 1F else 1F-percentage,
            if(percentage<0.5F) percentage else 1F, 0F
        )
        canvas?.drawRect(life, paint)
    }

}