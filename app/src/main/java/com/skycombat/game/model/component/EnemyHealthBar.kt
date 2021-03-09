package com.skycombat.game.model.component

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.enemy.Enemy

class EnemyHealthBar(var element : Enemy): HealthBar() {

    init{
        life=RectF(element.mov.left, element.mov.top+130f, element.mov.left+element.getWidth(),element.mov.top+140f)
    }
    /**
     * Updates the current and Maxhealth of these elements
     */
    override fun update() {
        percentage = element.getCurrentHealth() / element.getCurrentHealth().coerceAtLeast(element.getMaxHealth())
        val width = element.getWidth() * percentage
        life = RectF(element.mov.left, element.mov.top+element.getHeight()+20F, element.mov.left+width,element.mov.top+element.getHeight()+30F)
    }
}