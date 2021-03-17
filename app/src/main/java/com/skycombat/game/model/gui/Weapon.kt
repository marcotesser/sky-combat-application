package com.skycombat.game.model.gui

import com.skycombat.game.model.factory.bullet.BulletFactory
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.collision.CollisionStrategy
import com.skycombat.game.model.gui.properties.CanShoot

class Weapon(private val owner: CanShoot, var bulletFactory: BulletFactory, private var collisionStrategy: CollisionStrategy, private var direction: Bullet.Direction, private val dimension: DisplayDimension): Updatable {
    private var curUpdatesFromShot = 0

    @JvmName("setBullet1")
    fun setBulletType( bulletType: BulletFactory){
        this.bulletFactory=bulletType
    }

    fun generateBullet(): Bullet {
        val startPointOfShoot = owner.startPointOfShoot()
        return bulletFactory.generate(startPointOfShoot.x, startPointOfShoot.y, collisionStrategy, direction, dimension)
    }

    override fun update() {
        curUpdatesFromShot++
        if(curUpdatesFromShot >= bulletFactory.delayBetweenGenerations()){
            curUpdatesFromShot = 0
            owner.shoot()
        }
    }
}