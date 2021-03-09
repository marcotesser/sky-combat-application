package com.skycombat.game.model.enemy.movement

import com.skycombat.game.model.enemy.Enemy

interface Movement {
    fun move(enemy: Enemy)
}