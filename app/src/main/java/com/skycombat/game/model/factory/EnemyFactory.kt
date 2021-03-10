package com.skycombat.game.model.factory

import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.enemy.Enemy
import com.skycombat.game.model.gui.element.enemy.JetEnemy
import com.skycombat.game.model.gui.element.enemy.PlaneEnemy
import com.skycombat.game.model.gui.element.enemy.SpaceShipEnemy
import com.skycombat.game.model.gui.element.enemy.movement.Movement
import kotlin.random.Random

/**
 * Represents an Enemy Factory
 */
class EnemyFactory(var seed: Long) {

    private val random = Random(seed)

    /**
     * Generates the enemies
     * @return Enemy to the scene
     * @see Player
     */
    fun generate(): Enemy {
        val randMovement : Movement = when(random.nextInt(1,4)){
            1 -> Movement(4,3,600)
            2 -> Movement(3,4,500)
            3 -> Movement(6,2,700)
            4 -> Movement(2,5,400)
            else -> Movement(2,5,400)
        }

        val bulletType: Weapon.BulletType = when (random.nextInt(1, 4)) {
            1 -> Weapon.BulletType.LASER
            2 -> Weapon.BulletType.MULTIPLE
            3 -> Weapon.BulletType.GUST
            4 -> Weapon.BulletType.CLASSIC
            else -> Weapon.BulletType.CLASSIC
        }
        return when (random.nextInt(1,4)) {
            1 -> PlaneEnemy(bulletType, randMovement)
            2 -> JetEnemy(bulletType, randMovement)
            3 -> SpaceShipEnemy(bulletType, randMovement)
            else-> SpaceShipEnemy(bulletType, randMovement)
        }
    }
}