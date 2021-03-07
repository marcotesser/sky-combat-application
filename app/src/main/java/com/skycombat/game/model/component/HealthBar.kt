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
class HealthBar(private val initialLife: RectF, var paint: Paint, var element : HasHealth) : GUIComponent{
    private var life = RectF(initialLife);
    /**
     * Draws the healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(life, paint)
    }
    /**
     * Updates the current and Maxhealth of these elements
     */
    override fun update() {
        val curHealth = element.getCurrentHealth()
        val maxHealth = curHealth.coerceAtLeast(element.getMaxHealth())
        life.right = initialLife.left + (initialLife.right - initialLife.left) * (curHealth / maxHealth)
    }
}