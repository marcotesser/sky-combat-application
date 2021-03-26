package com.skycombat.game.model.gui.element

import android.graphics.PointF
import android.view.animation.LinearInterpolator
import com.skycombat.game.model.factory.bullet.BulletFactory
import com.skycombat.game.model.factory.bullet.LaserBulletFactory
import com.skycombat.game.model.geometry.Rectangle
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.ClassicBullet
import com.skycombat.game.model.gui.element.bullet.LaserBullet
import com.skycombat.game.model.gui.element.bullet.collision.EnemyCollisionStrategy
import com.skycombat.game.model.gui.element.enemy.Enemy
import com.skycombat.game.model.gui.element.enemy.JetEnemy
import com.skycombat.game.model.gui.element.enemy.movement.Movement
import com.skycombat.game.model.gui.element.ghost.movement.LinearAimedPositionMovement
import org.junit.Assert.*
import org.junit.Test

class PlayerTest {

    @Test
    fun `should remove if player is dead`(){
        var player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(10f, 10f))
        player.health = 0f

        assertTrue(player.shouldRemove())
    }

    @Test
    fun `shouldn't you be alive?`(){
        var player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(10f, 10f))
        player.health = 1f

        assertTrue(player.isAlive())
    }

    @Test
    fun `center is valid`(){
        val width = 100f; val height = 100f
        var player = Player(0f, LinearAimedPositionMovement(), DisplayDimension(width, height))

        val tx : Float  = width/2F
        val ty : Float = height/ 5 * 4

        val x = player.getX()
        val y = player.getY()

        assertTrue(tx==x && ty==y)
    }

    @Test
    fun `collide with bullet`(){
        val width = 100f; val height = 100f
//        var enemy = JetEnemy(LaserBulletFactory(), Movement(1,2,3), DisplayDimension(width, height))
        var player = Player(0f, LinearAimedPositionMovement(), DisplayDimension(width, height))
        var bullet = ClassicBullet(1f,1f, EnemyCollisionStrategy(),  Bullet.Direction.DOWN, DisplayDimension(width, height))

        player.setPosition(1f, 1f)

        assertTrue(player.collide(bullet))
    }

    @Test
    fun `check health if bullet collide`(){
        val width = 100f; val height = 100f
//        var enemy = JetEnemy(LaserBulletFactory(), Movement(1,2,3), DisplayDimension(width, height))
        var player = Player(0f, LinearAimedPositionMovement(), DisplayDimension(width, height))
        var bullet = ClassicBullet(1f,1f, EnemyCollisionStrategy(),  Bullet.Direction.DOWN, DisplayDimension(width, height))

        player.setPosition(1f, 1f)
        bullet.applyCollisionEffects(player)

        assertTrue(player.getCurrentHealth()<player.getMaxHealth())
    }

    @Test
    fun `check MAX_HEALTH with shield if bullet collide`(){
        val width = 100f; val height = 100f
        var player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(10f, 10f))
        var bullet = ClassicBullet(1f,1f, EnemyCollisionStrategy(),  Bullet.Direction.DOWN, DisplayDimension(width, height))

        player.applyShield(2)

        player.setPosition(1f, 1f)
        bullet.applyCollisionEffects(player)

        assertTrue(player.getCurrentHealth() == player.getMaxHealth())
    }



}

