package com.skycombat.game.model.factory.bullet

import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.MultipleBullet
import com.skycombat.game.model.gui.element.bullet.collision.CollisionStrategy

class MultipleBulletFactory : BulletFactory {
    override fun generate(x: Float, y: Float, collisionStrategy: CollisionStrategy, direction: Bullet.Direction, dimension: DisplayDimension): Bullet {
        val nextX = x - MultipleBullet.WIDTH/2F
        val nextY = y + if(collisionStrategy.canCollideTo(CollisionStrategy.Target.ENEMY))
            - MultipleBullet.HEIGHT/2F  else 0F
        return MultipleBullet(
                nextX,
                nextY,
                collisionStrategy,
                direction,
                dimension
        )
    }

    override fun delayBetweenGenerations(): Int {
        return 60
    }
}