package com.skycombat.game.model.gui.component

import android.graphics.RectF
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.Player

class PlayerHealthBar(var element : Player, val displayDimension: DisplayDimension): HealthBar() {

    private val initialLife:RectF

    init{
        life=RectF(20f, displayDimension.height - 50, displayDimension.width-20, displayDimension.height - 10)
        initialLife = RectF(life)
    }

    override fun update() {
        percentage = element.getCurrentHealth() / element.getCurrentHealth().coerceAtLeast(element.getMaxHealth())
        life.right = initialLife.left + (initialLife.right - initialLife.left) * percentage
    }
}