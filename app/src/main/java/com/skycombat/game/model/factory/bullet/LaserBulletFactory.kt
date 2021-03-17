package com.skycombat.game.model.factory.bullet

import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.LaserBullet
import com.skycombat.game.model.gui.element.bullet.strategy.CollisionStrategy

class LaserBulletFactory: BulletFactory {
    override fun generate(x: Float, y: Float, collisionStrategy: CollisionStrategy, direction: Bullet.Direction, dimension: DisplayDimension): Bullet {
        val nextY = y + if(collisionStrategy.canCollideTo(CollisionStrategy.Target.ENEMY))
            - LaserBullet.HEIGHT/2F  else 0F
        return LaserBullet(
                x,
                nextY,
                collisionStrategy,
                direction,
                dimension
        )
    }

    override fun delayBetweenGenerations(): Int {
        return 100
    }
}