package com.skycombat.game.model.factory.bullet

import com.skycombat.game.model.factory.powerup.LifePowerUpFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.collision.EnemyCollisionStrategy
import com.skycombat.game.model.gui.element.bullet.collision.PlayerCollisionStrategy
import com.skycombat.game.model.gui.element.enemy.JetEnemy
import com.skycombat.game.model.gui.element.enemy.movement.Movement
import com.skycombat.game.model.gui.element.ghost.movement.LinearAimedPositionMovement
import org.junit.Test

import org.junit.Assert.*
import kotlin.random.Random

class BulletFactoryTest {

    @Test
    fun generateClassicBulletFactory() {
        val width = 100f
        val height = 100f
        val power = ClassicBulletFactory()
        val classicBullet=power.generate(5f,2f, EnemyCollisionStrategy(), Bullet.Direction.UP, DisplayDimension(width, height))
        val player = Player(0f, LinearAimedPositionMovement(), DisplayDimension(width, height))
        player.health=player.getMaxHealth()

        classicBullet.applyCollisionEffects(player)
        assertEquals(player.getMaxHealth()-player.health, classicBullet.getDamage())
    }

    @Test
    fun generateGustBulletFactory() {
        val width = 100f
        val height = 100f
        val power = GustBulletFactory()
        val GustBullet=power.generate(5f,2f, EnemyCollisionStrategy(), Bullet.Direction.UP, DisplayDimension(width, height))
        val player = Player(0f, LinearAimedPositionMovement(), DisplayDimension(width, height))
        player.health=player.getMaxHealth()

        GustBullet.applyCollisionEffects(player)
        assertEquals(player.getMaxHealth()-player.health, GustBullet.getDamage())
    }

    @Test
    fun generateLaserBulletFactory() {
        val width = 100f
        val height = 100f
        val power = LaserBulletFactory()
        val LaserBullet=power.generate(5f,2f, PlayerCollisionStrategy(), Bullet.Direction.DOWN, DisplayDimension(width, height))
        val enemy = JetEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        LaserBullet.applyCollisionEffects(enemy)
        assertEquals(enemy.getMaxHealth()-enemy.health, LaserBullet.getDamage())
    }

    @Test
    fun generateMultipleBulletFactory() {
        val width = 100f
        val height = 100f
        val power = MultipleBulletFactory()
        val MultipleBullet=power.generate(5f,2f, PlayerCollisionStrategy(), Bullet.Direction.DOWN, DisplayDimension(width, height))
        val enemy = JetEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        MultipleBullet.applyCollisionEffects(enemy)
        assertEquals(enemy.getMaxHealth()-enemy.health, MultipleBullet.getDamage())
    }

    @Test
    fun `Test Delay Between Generations`(){
        val width = 100f
        val height = 100f
        val bf1 = ClassicBulletFactory()
        val bf2 = GustBulletFactory()
        val bf3 = LaserBulletFactory()
        val bf4 = MultipleBulletFactory()

        assertTrue(bf1.delayBetweenGenerations()==50 && bf2.delayBetweenGenerations()==10 && bf3.delayBetweenGenerations()==100 && bf4.delayBetweenGenerations()==60 )
    }
}