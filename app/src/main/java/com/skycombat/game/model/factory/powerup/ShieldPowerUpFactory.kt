package com.skycombat.game.model.factory.powerup

import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.powerup.PowerUp
import com.skycombat.game.model.gui.element.powerup.ShieldPowerUp
import kotlin.random.Random

class ShieldPowerUpFactory : PowerUpFactory {
    override fun generate(x: Float, iteration: Int, random: Random, displayDimension: DisplayDimension) : PowerUp {
        return ShieldPowerUp(
                x,
                0F,
                500 + iteration.toLong()*10,
                displayDimension
        )
    }
}