package com.skycombat.game.model.component

import android.graphics.*
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.support.Drawable
import com.skycombat.game.model.support.Updatable

/**
 * Represents the healthbar
 * @param initialLife : amount of initial life
 * @param paint : used to draw onto Canvas the healthbar
 * @param element : to determine if the element has health remaining
 */
abstract class HealthBar() : GUIComponent{

    var paint = Paint()
    lateinit var life: RectF;

    /**
     * Draws the healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(life, paint)
    }

}