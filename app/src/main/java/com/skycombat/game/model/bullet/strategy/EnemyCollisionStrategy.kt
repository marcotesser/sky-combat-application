package com.skycombat.game.model.bullet.strategy

import com.skycombat.game.model.Enemy
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.Player

class EnemyCollisionStrategy : CollisionStrategy{
    override fun shouldCollide(livingEntity: HasHealth): Boolean {
        return livingEntity !is Enemy
    }
}