package com.skycombat.game.model.factory.enemy

import com.skycombat.game.model.factory.bullet.ClassicBulletFactory
import com.skycombat.game.model.factory.bullet.GustBulletFactory
import com.skycombat.game.model.factory.bullet.LaserBulletFactory
import com.skycombat.game.model.factory.bullet.MultipleBulletFactory
import com.skycombat.game.model.factory.powerup.LifePowerUpFactory
import com.skycombat.game.model.gui.DisplayDimension
import org.junit.Test

import org.junit.Assert.*

class EnemyFactoryTest {

    @Test
    fun generate() {
        val width = 100f
        val height = 100f
        val enemygenerator = SeedGeneralEnemyFactory(10, DisplayDimension(width, height))
        val enemy= enemygenerator.generate()
        when(enemy.weapon.bulletFactory){
            LaserBulletFactory::class -> assertEquals(enemy.weapon.bulletFactory, LaserBulletFactory::class)
            GustBulletFactory::class -> assertEquals(enemy.weapon.bulletFactory, GustBulletFactory::class)
            MultipleBulletFactory::class -> assertEquals(enemy.weapon.bulletFactory, MultipleBulletFactory::class)
            ClassicBulletFactory::class -> assertEquals(enemy.weapon.bulletFactory, ClassicBulletFactory::class)
        }
    }
}