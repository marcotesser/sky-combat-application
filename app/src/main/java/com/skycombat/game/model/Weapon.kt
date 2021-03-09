package com.skycombat.game.model

import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.bullet.ClassicBullet
import com.skycombat.game.model.bullet.LaserBullet
import com.skycombat.game.model.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.enemy.Enemy
import com.skycombat.game.model.support.CanShoot
import com.skycombat.game.model.support.Updatable

class Weapon( val owner: CanShoot, var bulletType: BulletType, var collisionStrategy: CollisionStrategy): Updatable {

    /*
    La seguente enumerazione espone i tipo di proiettile gestiti da questo generatore di proiettili
     */
    enum class BulletType{
        CLASSIC, LASER
    }

    companion object{
        val UPDATES_BETWEEN_SHOTS= mapOf<BulletType, Int>(
            BulletType.CLASSIC to 60,
            BulletType.LASER to 100
        )
    }

    var curUpdatesFromShot = 0;

    @JvmName("setBullet1")
    fun setBulletType( bulletType: BulletType){
        this.bulletType=bulletType
    }

    fun generateBullet():Bullet{
        var startPointOfShoot = owner.startPointOfShoot()

        when(bulletType){
            BulletType.CLASSIC -> return ClassicBullet(
                startPointOfShoot.x, startPointOfShoot.y,collisionStrategy
            )
            BulletType.LASER -> return LaserBullet(
                startPointOfShoot.x,
                startPointOfShoot.y
                    + if(collisionStrategy.getTargetCollidable()==CollisionStrategy.Target.ENEMY)
                    - LaserBullet.HEIGHT  else 0F,
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