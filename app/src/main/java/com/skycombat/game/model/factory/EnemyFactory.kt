package com.skycombat.game.model.factory

import com.skycombat.game.GameView
import com.skycombat.game.model.enemy.Enemy
import com.skycombat.game.model.Player
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.Weapon
import com.skycombat.game.model.enemy.EnemyOne

/**
 * Represents an Enemy Factory
 * @param scene : the gameview onto which the enemies will be spawned
 */
class EnemyFactory(private val scene : GameView) {

    val context: ViewContext = ViewContext.getInstance()

    /**
     * Generates the enemies
     * @return Enemy to the scene
     * @see Player
     */
    fun generate() : Enemy {
        return EnemyOne(
            (context.getWidthScreen() - EnemyOne.WIDTH) / 2,
            100F,
            Weapon.BulletType.CLASSIC
        )
    }
}

