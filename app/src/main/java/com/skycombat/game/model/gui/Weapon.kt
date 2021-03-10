package com.skycombat.game.model.gui

import com.skycombat.game.model.gui.element.bullet.*
import com.skycombat.game.model.gui.element.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.gui.properties.CanShoot

class Weapon(val owner: CanShoot, var bulletType: BulletType, var collisionStrategy: CollisionStrategy): Updatable {

    /*
    La seguente enumerazione espone i tipo di proiettile gestiti da questo generatore di proiettili
     */
    enum class BulletType{
        CLASSIC, LASER, MULTIPLE, GUST
    }

    companion object{
        val UPDATES_BETWEEN_SHOTS= mapOf<BulletType, Int>(
            BulletType.CLASSIC to 50,
            BulletType.LASER to 100,
            BulletType.MULTIPLE to 60,
            BulletType.GUST to 10
        )
    }

    var curUpdatesFromShot = 0;

    @JvmName("setBullet1")
    fun setBulletType( bulletType: BulletType){
        this.bulletType=bulletType
    }

    fun generateBullet(): Bullet {
        var startPointOfShoot = owner.startPointOfShoot()
        when(bulletType){
            BulletType.CLASSIC -> return ClassicBullet(
                startPointOfShoot.x, startPointOfShoot.y,collisionStrategy
            )
            BulletType.LASER -> return LaserBullet(
                startPointOfShoot.x,
                startPointOfShoot.y
                    + if(collisionStrategy.getTargetCollidable()== CollisionStrategy.Target.ENEMY)
                    - LaserBullet.HEIGHT/2F  else 0F,
                collisionStrategy
            )
            BulletType.GUST -> return GustBullet(
                startPointOfShoot.x, startPointOfShoot.y,collisionStrategy
            )
            BulletType.MULTIPLE -> return MultipleBullet(
                startPointOfShoot.x - MultipleBullet.WIDTH/2F,
                startPointOfShoot.y
                        + if(collisionStrategy.getTargetCollidable()== CollisionStrategy.Target.ENEMY)
                    - MultipleBullet.HEIGHT/2F  else 0F,
                collisionStrategy
            )
        }
    }

    override fun update() {
        curUpdatesFromShot++
        if(curUpdatesFromShot >= UPDATES_BETWEEN_SHOTS[bulletType]!!){
            curUpdatesFromShot = 0;
            owner.shoot();
        }
    }
}