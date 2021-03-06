package com.skycombat.game.model.factory

import com.skycombat.game.GameView
import com.skycombat.game.model.LifePowerUp

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
            50F,
            Math.random().toFloat() * scene.getMaxWidth(),
            0f,
            10f,
            scene,
            20F
        )
    }
}