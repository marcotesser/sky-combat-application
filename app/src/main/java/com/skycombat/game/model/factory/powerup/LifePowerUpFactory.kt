package com.skycombat.game.model.factory.powerup

import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.powerup.LifePowerUp
import com.skycombat.game.model.gui.element.powerup.PowerUp
import kotlin.random.Random

class LifePowerUpFactory : PowerUpFactory {
    override fun generate(x: Float, iteration: Int, random: Random, displayDimension: DisplayDimension) : PowerUp {
        return LifePowerUp(
                x,
                0F,
                500F + iteration * 10F,
                displayDimension
        )
    }
}