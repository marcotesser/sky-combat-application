package com.skycombat.game.model.component

import android.content.Context
import android.graphics.*
import android.util.Log
import com.skycombat.game.GameView
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.Player;

import java.util.*
import kotlin.math.pow
/**
 * Represents the healthbar
 * @param initialLife : amount of initial life
 * @param paint : used to draw onto Canvas the healthbar
 * @param element : to determine if the element has health remaining
 */
class HealthBar(private val initialLife: RectF, var paint: Paint, var element : HasHealth) {
    private var life = RectF(initialLife);
    /**
     * Draws the healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     */
    fun draw(canvas: Canvas?) {
        canvas?.drawRect(life, paint)
    }
    /**
     * Updates the current and Maxhealth of these elements
     */
    fun update() {
        val curHealth = element.getCurrentHealth()
        val maxHealth = curHealth.coerceAtLeast(element.getMaxHealth())
        life.right = initialLife.left + (initialLife.right - initialLife.left) * (curHealth / maxHealth)
    }


}