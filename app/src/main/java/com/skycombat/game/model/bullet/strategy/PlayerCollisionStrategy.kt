package com.skycombat.game.model.bullet.strategy

import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.Player

class PlayerCollisionStrategy : CollisionStrategy{
    override fun shouldCollide(livingEntity: HasHealth): Boolean {
        return livingEntity !is Player
    }
}