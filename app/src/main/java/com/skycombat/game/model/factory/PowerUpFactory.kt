package com.skycombat.game.model.factory

import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.element.powerup.GunsPowerUp
import com.skycombat.game.model.gui.element.powerup.LifePowerUp
import com.skycombat.game.model.gui.element.powerup.PowerUp
import com.skycombat.game.model.gui.element.powerup.ShieldPowerUp
import kotlin.random.Random

/**
 * Represents a Life PowerUp Factory
 */
class PowerUpFactory(var seed : Long, val displayDimension: DisplayDimension) {
    enum class PowerUpType {
        LIFE {
            override fun generate(x: Float, iteration: Int, random: Random, displayDimension: DisplayDimension) : PowerUp {
                return LifePowerUp(
                    x,
                    0F,
                    500F + iteration * 10F,
                    displayDimension
                )
            }
        }, SHIELD {
            override fun generate(x: Float, iteration: Int, random: Random, displayDimension: DisplayDimension) : PowerUp {
                return ShieldPowerUp(
                    x,
                    0F,
                    500 + iteration.toLong()*10,
                    displayDimension
                )
            }
        }, GUNS {
            override fun generate(x: Float, iteration: Int, random: Random, displayDimension: DisplayDimension) : PowerUp {
                val improvement = if(iteration < 20) 6 * iteration / 20 else 6
                 val bulletType =  when(random.nextInt(1, 4 + improvement)){
                    1 -> Weapon.BulletType.CLASSIC
                    2,5 -> Weapon.BulletType.GUST
                    3,6,8 -> Weapon.BulletType.MULTIPLE
                    4,7,9 -> Weapon.BulletType.LASER
                    else -> Weapon.BulletType.CLASSIC
                }
                return GunsPowerUp(
                    x,
                    0F,
                    bulletType,
                    displayDimension
                )
            }
        };
        abstract fun generate(x: Float, iteration: Int, random: Random, displayDimension: DisplayDimension) : PowerUp
    }

    private val random = Random(seed)
    var numPowerUpGenerated: Int = 0

    /**
     * Generates the powerups
     * @return LifePowerUp with an health increase
     */
    fun generate(): PowerUp {
        numPowerUpGenerated++
        return (when(random.nextInt(0,3)) {
            0 -> PowerUpType.LIFE
            1 -> PowerUpType.GUNS
            2 -> PowerUpType.SHIELD
            else -> PowerUpType.LIFE
        }).generate(
            Math.random().toFloat() * displayDimension.width,
            numPowerUpGenerated,
            random,
            displayDimension
        )
    }

}
