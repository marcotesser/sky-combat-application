package com.skycombat.game.model.gui.element.powerup

import com.skycombat.game.model.factory.bullet.LaserBulletFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.collision.PlayerCollisionStrategy
import com.skycombat.game.model.gui.element.ghost.movement.LinearAimedPositionMovement
import org.junit.Test

import org.junit.Assert.*

class PowerUpTest {

    @Test
    fun `shouldApply LifePowerUp`() {
        val width = 100f;
        val height = 100f
        var player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(10f, 10f))
        player.health = 0f
        val power = LifePowerUp(1f, 1f, 2f, DisplayDimension(width, height))

        player.setPosition(1f, 1f)
        power.applyPowerUPEffects(player)

        assertTrue(power.shouldApply(player))
    }
    @Test
    fun `shouldApply GunsPowerUp`() {
        val width = 100f
        val height = 100f
        var player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(10f, 10f))
        val laser=LaserBulletFactory().generate(1f, 1f, PlayerCollisionStrategy(), Bullet.Direction.DOWN, DisplayDimension(width, height))
        val power = GunsPowerUp(1f, 1f, LaserBulletFactory(), DisplayDimension(width, height))

        player.setPosition(1f, 1f)
        power.applyPowerUPEffects(player)

        assertTrue(power.shouldApply(player))
    }

    @Test
    fun update() {
        val width = 2f
        val height = 10f
        var player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(10f, 10f))
        val power = GunsPowerUp(1f, 1f, LaserBulletFactory(), DisplayDimension(width, height))
        
        power.update()
        assertTrue(power.collide(player))
    }

    @Test
    fun draw() {
    }

    @Test
    fun shouldRemove() {
        val width = 100f;
        val height = 100f
        var player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(10f, 10f))
        val power = ShieldPowerUp(1f, 1f, 2, DisplayDimension(width, height))
        power.applyPowerUPEffects(player)

        assertTrue(power.shouldRemove())
    }

}