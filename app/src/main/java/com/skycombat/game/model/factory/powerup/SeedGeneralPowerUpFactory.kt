package com.skycombat.game.model.factory.powerup

import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.powerup.PowerUp
import kotlin.random.Random

class SeedGeneralPowerUpFactory (var seed : Long, val displayDimension: DisplayDimension) : GeneralPowerUpFactory{
    private val random = Random(seed)
    var numPowerUpGenerated: Int = 0

    /**
     * Generates the powerups
     * @return LifePowerUp with an health increase
     */
    override fun generate(): PowerUp {
        numPowerUpGenerated++
        return (when(random.nextInt(0,3)) {
            0 -> LifePowerUpFactory()
            1 -> GunsPowerUpFactory()
            2 -> ShieldPowerUpFactory()
            else -> LifePowerUpFactory()
        }).generate(
                Math.random().toFloat() * displayDimension.width,
                numPowerUpGenerated,
                random,
                displayDimension
        )
    }

}