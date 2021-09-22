package com.skycombat.game.model.factory.enemy

import com.skycombat.game.model.gui.element.enemy.Enemy

/**
 * Represents an Enemy Factory
 */
interface GeneralEnemyFactory {
    fun generate(): Enemy
}