package com.skycombat.game.model.bullet.strategy

import com.skycombat.game.model.HasHealth

interface CollisionStrategy {

    enum class Target {
        PLAYER, ENEMY
    }
    fun shouldCollide(livingEntity:HasHealth):Boolean
    fun getTargetCollidable():Target
}