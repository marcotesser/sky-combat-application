package com.skycombat.game.model

import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.bullet.ClassicBullet
import com.skycombat.game.model.bullet.LaserBullet
import com.skycombat.game.model.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.support.Updatable
import kotlin.reflect.KClass

class Weapon( val owner: Any, var bulletType:BulletType, var collisionStrategy: CollisionStrategy): Updatable {

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
        var x:Float?= null; var y:Float?=null; var target:Bullet.Target?=null;

        if(owner is Player){
            x=owner.positionX
            y=owner.positionY - Player.RADIUS -2F
            target=Bullet.Target.ENEMY

        }else if(owner is Enemy){
            x=owner.getPosition().centerX()
            y=owner.top+owner.height +2F
            target=Bullet.Target.PLAYER
        }

        when(bulletType){
            BulletType.CLASSIC -> return ClassicBullet(x!!, y!!, target!!,collisionStrategy)
            BulletType.LASER -> return LaserBullet(x!!, y!!, target!!,collisionStrategy)
        }
    }

    override fun update() {
        curUpdatesFromShot++
        if(curUpdatesFromShot >= UPDATES_BETWEEN_SHOTS[bulletType]!!){
            curUpdatesFromShot = 0;
            if(owner is Player) owner.shoot();
            if(owner is Enemy) owner.shoot();
        }
    }
}