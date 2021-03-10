package com.skycombat.game.model.gui.element.bullet.strategy

import com.skycombat.game.model.gui.element.enemy.Enemy
import com.skycombat.game.model.gui.properties.HasHealth

class EnemyCollisionStrategy : CollisionStrategy {
    override fun shouldCollide(livingEntity: HasHealth): Boolean {
        return livingEntity !is Enemy
    }

    override fun getTargetCollidable(): CollisionStrategy.Target {
        return CollisionStrategy.Target.PLAYER
    }
}