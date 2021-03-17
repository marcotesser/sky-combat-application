package com.skycombat.game.model.gui.element.bullet.collision

import com.skycombat.game.model.gui.properties.HasHealth

interface CollisionStrategy {

    enum class Target {
        PLAYER, ENEMY
    }
    fun shouldCollide(livingEntity: HasHealth):Boolean
    fun canCollideTo(target: Target) : Boolean
}