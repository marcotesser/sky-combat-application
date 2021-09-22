package com.skycombat.game.model.factory.powerup

import com.skycombat.game.model.gui.element.powerup.PowerUp

/**
 * Represents a Life PowerUp Factory
 */
interface GeneralPowerUpFactory{
    /**
     * Generates the powerups
     * @return LifePowerUp with an health increase
     */
    fun generate(): PowerUp

}
