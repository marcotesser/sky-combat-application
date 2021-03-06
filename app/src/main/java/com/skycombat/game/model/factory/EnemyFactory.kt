package com.skycombat.game.model.factory

import com.skycombat.game.GameView
import com.skycombat.game.model.Enemy
/**
 * Represents an Enemy Factory
 * @param scene : the gameview onto which the enemies will be spawned
 */
class EnemyFactory(private val scene : GameView) {
    /**
     * Generates the enemies
     * @return Enemy to the scene
     * @see Player
     */
    fun generate() : Enemy{
        val width = 150F;
        val height = 100F;
        return Enemy(
            (scene.getMaxWidth().toFloat() - width) / 2,
            100F,
            width,
            height,
            2F,
            scene
        )
    }
}

