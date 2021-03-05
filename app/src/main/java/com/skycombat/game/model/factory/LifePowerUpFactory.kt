package com.skycombat.game.model.factory

import com.skycombat.game.GameView
import com.skycombat.game.model.Enemy
import com.skycombat.game.model.powerup.LifePowerUp

/**
 * Represents a Life PowerUp Factory
 * @param scene : the gameview onto which the life powerups will be spawned
 */
class LifePowerUpFactory(private val scene : GameView) {
    /**
     * Generates the powerups
     * @return LifePowerUp with an health increase
     */
    fun generate() : LifePowerUp {
        return LifePowerUp(
            50,
            Math.random().toFloat() * scene.getMaxWidth(),
            0f
        )
    }
}