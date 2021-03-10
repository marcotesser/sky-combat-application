package com.skycombat.game.model.gui.element.bullet.strategy

import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.properties.HasHealth

class PlayerCollisionStrategy : CollisionStrategy {
    override fun shouldCollide(livingEntity: HasHealth): Boolean {
        return livingEntity !is Player
    }

    override fun canCollideTo(target: CollisionStrategy.Target): Boolean {
        return CollisionStrategy.Target.ENEMY == target
    }
}