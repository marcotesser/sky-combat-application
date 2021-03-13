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
    private var iteration = 0
    /**
     * Generates the enemies
     * @return Enemy to the scene
     * @see Player
     */
    fun generate(): Enemy {
        iteration++;

        val randMovement : Movement = when(random.nextInt(1,11)){
            1 -> Movement(4,3,600 + iteration * 10)
            2,5 -> Movement(3,4,500 + iteration * 10)
            3,6,8 -> Movement(6,2,700 + iteration * 10)
            4,7,9,10 -> Movement(2,5,400 + iteration * 10)
            else -> Movement(2,5,400 + iteration * 10)
        }

        val bulletType: Weapon.BulletType = when (random.nextInt(1, 11)) {
            1,5,8,10 -> Weapon.BulletType.LASER
            2,6,9 -> Weapon.BulletType.MULTIPLE
            3,7 -> Weapon.BulletType.GUST
            4 -> Weapon.BulletType.CLASSIC
            else -> Weapon.BulletType.CLASSIC
        }

        return when (random.nextInt(1,7)) {
            1 -> PlaneEnemy(bulletType, randMovement, 1 + (iteration.toFloat() / 3))
            2,4 -> JetEnemy(bulletType, randMovement)
            3,5,6 -> SpaceShipEnemy(bulletType, randMovement)
            else-> SpaceShipEnemy(bulletType, randMovement)
        }
    }
}