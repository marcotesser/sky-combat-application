package com.skycombat.game.model.gui.element.enemy

import com.skycombat.game.model.factory.bullet.GustBulletFactory
import com.skycombat.game.model.factory.bullet.LaserBulletFactory
import com.skycombat.game.model.factory.bullet.MultipleBulletFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.collision.PlayerCollisionStrategy
import com.skycombat.game.model.gui.element.enemy.movement.Movement
import com.skycombat.game.model.gui.element.ghost.movement.LinearAimedPositionMovement
import org.junit.Test

import org.junit.Assert.*

class EnemyTest {

    @Test
    fun update() {
        //val width = 100f; val height = 100f
        //var enemy =PlaneEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        //while (!enemy.shouldRemove())
            //enemy.update()
        //assert()
    }

    @Test
    fun getMaxHealth() {
        val width = 100f; val height = 100f
        var coeff = 6f
        var enemy = PlaneEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height), coeff)
        var health = 200f * coeff
        assertEquals(health , enemy.getMaxHealth())
    }

    @Test
    fun getHeight() {
        val width = 100f
        val height = 100f
        var enemy = JetEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        val laser= MultipleBulletFactory().generate(1f, 1f, PlayerCollisionStrategy(), Bullet.Direction.DOWN, DisplayDimension(width, height))

    }

    @Test
    fun shouldRemove() {
        val width = 100f; val height = 100f
        var enemy = SpaceShipEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        enemy.health = 0f

        assertTrue(enemy.shouldRemove())
    }


//    @Test
//    fun `exit screeen`(){
//
//        val width = 100f; val height = 100f
//        var enemy = SpaceShipEnemy(GustBulletFactory(), Movement(4,3,600),  DisplayDimension(width, height))
//
//        while (!enemy.shouldRemove())
//            enemy.update()
//
//        assertTrue(enemy.shouldRemove())
//    }

}