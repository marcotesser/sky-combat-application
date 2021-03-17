package com.skycombat.game.model.factory.bullet

import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.ClassicBullet
import com.skycombat.game.model.gui.element.bullet.collision.CollisionStrategy

class ClassicBulletFactory : BulletFactory {
    override fun generate(x: Float, y: Float, collisionStrategy: CollisionStrategy, direction: Bullet.Direction, dimension: DisplayDimension): Bullet {
        return ClassicBullet(x, y, collisionStrategy, direction, dimension)
    }

    override fun delayBetweenGenerations(): Int {
        return 50
    }
}