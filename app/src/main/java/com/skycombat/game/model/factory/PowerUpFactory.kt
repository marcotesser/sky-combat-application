package com.skycombat.game.model.factory

import com.skycombat.game.model.factory.bullet.*
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
                val bulletFactory: BulletFactory = when (random.nextInt(1, 11)) {
                    1,5,8,10 -> LaserBulletFactory()
                    2,6,9 -> MultipleBulletFactory()
                    3,7 -> GustBulletFactory()
                    4 -> ClassicBulletFactory()
                    else -> ClassicBulletFactory()
                }
                return GunsPowerUp(
                    x,
                    0F,
                    bulletFactory,
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
