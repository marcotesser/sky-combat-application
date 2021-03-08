package com.skycombat.game.model.component

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.enemy.Enemy

class EnemyHealthBar(var element : Enemy): HealthBar() {

    init{
        life=RectF(element.left, element.top+130f, element.left+element.getWidth(),element.top+140f)
        paint.color= Color.YELLOW
    }
    /**
     * Updates the current and Maxhealth of these elements
     */
    override fun update() {
        val curHealth = element.getCurrentHealth()
        val maxHealth = curHealth.coerceAtLeast(element.getMaxHealth())
        val width = element.getWidth() * (curHealth / maxHealth)
        life = RectF(element.left, element.top+130f, element.left+width,element.top+140f)
    }
}