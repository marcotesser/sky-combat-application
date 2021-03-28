package com.skycombat.game.model.gui.element.bullet

import com.skycombat.game.model.factory.bullet.GustBulletFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.bullet.collision.CollisionStrategy
import com.skycombat.game.model.gui.element.bullet.collision.EnemyCollisionStrategy
import com.skycombat.game.model.gui.element.bullet.collision.PlayerCollisionStrategy
import com.skycombat.game.model.gui.element.enemy.JetEnemy
import com.skycombat.game.model.gui.element.enemy.PlaneEnemy
import com.skycombat.game.model.gui.element.enemy.movement.Movement
import com.skycombat.game.model.gui.element.ghost.movement.LinearAimedPositionMovement
import org.junit.Test

import org.junit.Assert.*

class BulletTest {

    @Test
    fun `shouldRemove gust bullet`() {
        val width = 100f
        val height = 100f
        val bullet = GustBullet(2f, 2f, PlayerCollisionStrategy(), Bullet.Direction.UP, DisplayDimension(width, height))
        val enemy = PlaneEnemy(GustBulletFactory(), Movement(1, 2, 3), DisplayDimension(width, height))
        bullet.applyCollisionEffects(enemy)
        assertTrue(bullet.shouldRemove())
    }
    @Test
    fun `check life damage laser bullet`() {
        val width = 100f
        val height = 100f
        val bullet = LaserBullet(2f, 2f, PlayerCollisionStrategy(), Bullet.Direction.UP, DisplayDimension(width, height))
        val enemy = PlaneEnemy(GustBulletFactory(), Movement(1, 2, 3), DisplayDimension(width, height))
        bullet.applyCollisionEffects(enemy)
        assertTrue(enemy.health<enemy.getMaxHealth())
    }

    @Test
    fun `getSpeed classic bullet`() {
        val width = 10f
        val height = 100f
        val bullet = ClassicBullet(5f, 0f, EnemyCollisionStrategy(), Bullet.Direction.DOWN, DisplayDimension(width, height))
        val player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(width, height))

        player.setPosition(5f,100f)
        player.health = 1f

        for (i in 1..2)bullet.update()

        if(player.collide(bullet))
            bullet.applyCollisionEffects(player)

        assertTrue(bullet.shouldRemove() && player.shouldRemove())
    }

    @Test
    fun `getSpeed gust bullet`() {
        val width = 10f
        val height = 100f
        val bullet = GustBullet(5f, 0f, EnemyCollisionStrategy(), Bullet.Direction.DOWN, DisplayDimension(width, height))
        val player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(width, height))

        player.setPosition(5f,100f)
        player.health = 1f

        for (i in 1..2)bullet.update()

        if(player.collide(bullet))
            bullet.applyCollisionEffects(player)

        assertTrue(bullet.shouldRemove() && player.shouldRemove())
    }

//    @Test
//    fun `getSpeed laser bullet`() {
//        val width = 10f
//        val height = 270f
//        val bullet = LaserBullet(5f, 0f, EnemyCollisionStrategy(), Bullet.Direction.DOWN, DisplayDimension(width, height))
//        val player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(width, height))
//
//        player.setPosition(5f,height)
//        player.health = 1f
//
//        bullet.update()
//
//        if(player.collide(bullet))
//            bullet.applyCollisionEffects(player)
//
//        assertTrue(bullet.shouldRemove() && player.shouldRemove())
//    }
//
//    @Test
//    fun `getSpeed multiple bullet`() {
//        val width = 10f
//        val height = 119f
//        val bullet = MultipleBullet(5f, 0f, EnemyCollisionStrategy(), Bullet.Direction.DOWN, DisplayDimension(width, height))
//        val player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(width, height))
//
//        player.setPosition(5f,height)
//        player.health = 1f
//
//        for (i in 1..2)bullet.update()
//
//        if(bullet.collide(player))
//            bullet.applyCollisionEffects(player)
//
//        assertTrue(bullet.shouldRemove() && player.shouldRemove())
//    }

}