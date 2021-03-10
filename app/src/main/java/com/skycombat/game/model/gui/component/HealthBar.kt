package com.skycombat.game.model.gui.component

import android.graphics.*

/**
 * Represents the healthbar
 * @param initialLife : amount of initial life
 * @param paint : used to draw onto Canvas the healthbar
 * @param element : to determine if the element has health remaining
 */
abstract class HealthBar() : GUIComponent {

    var paint = Paint()
    lateinit var life: RectF;
    var percentage:Float =1F

    /**
     * Draws the healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     */
    abstract fun update();
    override fun draw(canvas: Canvas?) {
        this.update();
        paint.color = Color.rgb(
            if(percentage<0.5F) 1F else 1F-percentage,
            if(percentage<0.5F) percentage else 1F,
            0F)
        canvas?.drawRect(life, paint)
    }

}