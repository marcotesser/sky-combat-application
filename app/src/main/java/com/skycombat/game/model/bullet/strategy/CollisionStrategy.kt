package com.skycombat.game.model.bullet.strategy

import com.skycombat.game.model.HasHealth

interface CollisionStrategy {
    fun shouldCollide(livingEntity:HasHealth):Boolean
}