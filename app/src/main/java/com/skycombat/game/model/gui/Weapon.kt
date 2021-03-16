package com.skycombat.game.model.gui

import com.skycombat.game.model.gui.element.bullet.*
import com.skycombat.game.model.gui.element.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.gui.properties.CanShoot

class Weapon(private val owner: CanShoot, var bulletType: BulletType, private var collisionStrategy: CollisionStrategy, private var direction: Bullet.Direction, private val dimension: DisplayDimension): Updatable {

    enum class BulletType(private val delayTime: Int){
        CLASSIC(50) {
            override fun generate(x: Float, y: Float, collisionStrategy: CollisionStrategy, direction: Bullet.Direction, dimension: DisplayDimension): Bullet {
                return ClassicBullet(x, y, collisionStrategy, direction, dimension)
            }
        },
        LASER(100) {
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
        },
        MULTIPLE(60) {
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
        },
        GUST(10) {
            override fun generate(x: Float, y: Float, collisionStrategy: CollisionStrategy, direction: Bullet.Direction, dimension: DisplayDimension): Bullet {
                return GustBullet(x, y,collisionStrategy, direction, dimension)
            }
        };
        fun delay(): Int{
            return delayTime
        }
        abstract fun generate(x: Float, y : Float, collisionStrategy: CollisionStrategy, direction: Bullet.Direction, dimension: DisplayDimension) : Bullet
    }
    private var curUpdatesFromShot = 0

    @JvmName("setBullet1")
    fun setBulletType( bulletType: BulletType){
        this.bulletType=bulletType
    }

    fun generateBullet(): Bullet {
        val startPointOfShoot = owner.startPointOfShoot()
        return bulletType.generate(startPointOfShoot.x, startPointOfShoot.y, collisionStrategy, direction, dimension)
    }

    override fun update() {
        curUpdatesFromShot++
        if(curUpdatesFromShot >= bulletType.delay()){
            curUpdatesFromShot = 0
            owner.shoot()
        }
    }
}