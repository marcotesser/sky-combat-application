package com.skycombat.game.model.gui.element.enemy

import com.skycombat.game.model.factory.bullet.GustBulletFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.enemy.movement.Movement
import com.skycombat.game.model.gui.element.ghost.movement.LinearAimedPositionMovement
import org.junit.Test

import org.junit.Assert.*

class EnemyTest {

    @Test
    fun update() {
    }

    @Test
    fun getWidth() {
    }

    @Test
    fun getHeight() {
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