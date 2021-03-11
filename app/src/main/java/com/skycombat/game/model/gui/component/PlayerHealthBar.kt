package com.skycombat.game.model.gui.component

import android.graphics.RectF
import com.skycombat.game.scene.ViewContext
import com.skycombat.game.model.gui.element.Player

class PlayerHealthBar(var element : Player): HealthBar() {

    var context: ViewContext = ViewContext.getInstance()
    val initialLife:RectF

    init{
        life=RectF(20f, context.getHeightScreen() - 50, context.getWidthScreen()-20, context.getHeightScreen() - 10)
        initialLife = RectF(life)
    }

    override fun update() {
        percentage = element.getCurrentHealth() / element.getCurrentHealth().coerceAtLeast(element.getMaxHealth())
        life.right = initialLife.left + (initialLife.right - initialLife.left) * percentage
    }
}