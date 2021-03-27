package com.skycombat.game.model.gui.element.enemy

import com.skycombat.game.model.factory.bullet.ClassicBulletFactory
import com.skycombat.game.model.factory.bullet.GustBulletFactory
import com.skycombat.game.model.factory.bullet.LaserBulletFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.enemy.movement.Movement

import org.junit.Test

import org.junit.Assert.*

class EnemyTest {

    @Test
    fun getHeight() {
        val width = 100f
        val height = 100f
        val enemy = JetEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        val high = 180f
        assertNotEquals(high,enemy.getHeight())
    }

    @Test
    fun `getMaxHealth plane enemy`() {
        val width = 100f
        val height = 100f
        val coeff = 6f
        val enemy = PlaneEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height), coeff)
        val health = 200f * coeff
        assertEquals(health , enemy.getMaxHealth())
    }

    @Test
    fun `getMaxHealth jet enemy`() {
        val width = 100f
        val height = 100f
        val enemy = JetEnemy(LaserBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        val health = 300f
        assertEquals(health , enemy.getMaxHealth())
    }

    @Test
    fun shouldRemove() {
        val width = 100f
        val height = 100f
        val enemy = SpaceShipEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        enemy.health = 0f

        assertTrue(enemy.shouldRemove())
    }

    @Test
    fun `getPosition spaceShip`(){
        val width = 100f
        val height = 100f
        val enemy = SpaceShipEnemy(GustBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        assertNotSame(1f,enemy.getPosition())
    }

    @Test
    fun `getPosition plane`(){
        val width = 100f
        val height = 100f
        val enemy = PlaneEnemy(ClassicBulletFactory(), Movement(1,2,3),  DisplayDimension(width, height))
        assertNotSame(1f,enemy.getPosition())
    }
}