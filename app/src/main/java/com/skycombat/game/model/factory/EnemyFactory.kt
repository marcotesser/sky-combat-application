package com.skycombat.game.model.factory

import com.skycombat.game.model.enemy.Enemy
import com.skycombat.game.model.Player
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.Weapon
import com.skycombat.game.model.enemy.EnemyOne
import com.skycombat.game.model.enemy.EnemyThree
import com.skycombat.game.model.enemy.EnemyTwo
import com.skycombat.game.model.enemy.movement.Movement
import kotlin.random.Random

/**
 * Represents an Enemy Factory
 * @param scene : the gameview onto which the enemies will be spawned
 */
class EnemyFactory(var seed: Long) {

    val random = Random(seed);
    val context: ViewContext = ViewContext.getInstance()
    var randEnemy = 1
    var randMovement = 1
    var randGuns = 1
    var numEnemyGenerated: Int = 0

    /**
     * Generates the enemies
     * @return Enemy to the scene
     * @see Player
     */
    fun generate(): Enemy {

        numEnemyGenerated++
        var bulletType: Weapon.BulletType
        randEnemy= random.nextInt(1,4)
        randMovement= random.nextInt(1,5)
        randGuns= random.nextInt(1, 4)

        when (randGuns) {
            1 -> bulletType = Weapon.BulletType.LASER
            2 -> bulletType = Weapon.BulletType.MULTIPLE
            3 -> bulletType = Weapon.BulletType.GUST
            4 -> bulletType = Weapon.BulletType.CLASSIC
            else -> bulletType = Weapon.BulletType.CLASSIC
        }
        when (randEnemy) {
            1 -> {
                return EnemyOne(
                    bulletType, Movement(randMovement)) }
            2 -> {
                return EnemyTwo(
                    bulletType, Movement(randMovement)) }
            3 -> {
                return EnemyThree(
                    bulletType, Movement(randMovement)) }
            else->return EnemyThree(
                bulletType, Movement(randMovement)) }
    }

    fun getnumenemygenerated(): Int {
        return numEnemyGenerated
    }

}