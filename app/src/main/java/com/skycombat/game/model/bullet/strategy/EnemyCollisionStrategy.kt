package com.skycombat.game.model.bullet.strategy

import com.skycombat.game.model.enemy.Enemy
import com.skycombat.game.model.HasHealth

class EnemyCollisionStrategy : CollisionStrategy{
    override fun shouldCollide(livingEntity: HasHealth): Boolean {
        return livingEntity !is Enemy
    }

    override fun getTargetCollidable(): CollisionStrategy.Target {
        return CollisionStrategy.Target.PLAYER
    }
}