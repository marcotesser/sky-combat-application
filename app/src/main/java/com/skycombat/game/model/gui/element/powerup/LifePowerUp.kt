package com.skycombat.game.model.gui.element.powerup

import com.skycombat.R
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.Player

class LifePowerUp(x: Float, y: Float, var healthIncrease: Float, val dimension: DisplayDimension)
    : PowerUp(x,y, dimension) {

    override var powerUpImg: Int = R.drawable.lifepowerup

    override fun applyPowerUPEffects(player: Player){
        player.updateHealth(healthIncrease)
        this.used = true
    }
}