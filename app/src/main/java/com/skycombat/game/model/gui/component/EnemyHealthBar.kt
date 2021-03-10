package com.skycombat.game.model.gui.component

import android.graphics.RectF
import com.skycombat.game.model.gui.element.enemy.Enemy

class EnemyHealthBar(var element : Enemy): HealthBar() {

    init{
        life=RectF(element.left, element.top+130f, element.left+element.getWidth(),element.top+140f)
    }
    /**
     * Updates the current and Maxhealth of these elements
     */
    override fun update() {
        percentage = element.getCurrentHealth() / element.getCurrentHealth().coerceAtLeast(element.getMaxHealth())
        val width = element.getWidth() * percentage
        life = RectF(element.left, element.top+element.getHeight()+20F, element.left+width,element.top+element.getHeight()+30F)
    }
}