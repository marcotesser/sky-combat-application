package com.skycombat.game.model.factory.bullet

import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.collision.CollisionStrategy

interface BulletFactory {
    fun generate(x: Float, y : Float, collisionStrategy: CollisionStrategy, direction: Bullet.Direction, dimension: DisplayDimension) : Bullet

    // TODO cercare modo migliore per mettere delay personalizzati
    fun delayBetweenGenerations() : Int
}