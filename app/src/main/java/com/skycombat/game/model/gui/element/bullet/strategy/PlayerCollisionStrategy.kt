package com.skycombat.game.model.gui.element.bullet.strategy

import com.skycombat.game.model.gui.properties.HasHealth
import com.skycombat.game.model.gui.element.Player

class PlayerCollisionStrategy : CollisionStrategy {
    override fun shouldCollide(livingEntity: HasHealth): Boolean {
        return livingEntity !is Player
    }

    override fun getTargetCollidable(): CollisionStrategy.Target {
        return CollisionStrategy.Target.ENEMY
    }
}