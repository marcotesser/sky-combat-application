package com.skycombat.game.model.factory

import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.element.powerup.GunsPowerUp
import com.skycombat.game.model.gui.element.powerup.LifePowerUp
import com.skycombat.game.model.gui.element.powerup.PowerUp
import com.skycombat.game.model.gui.element.powerup.ShieldPowerUp
import kotlin.random.Random

/**
 * Represents a Life PowerUp Factory
 * @param scene : the gameview onto which the life powerups will be spawned
 */
class PowerUpFactory(var seed:Long) {

    enum class PowerUpType {
        LIFE, SHIELD, GUNS
    }

    val random = Random(seed);
    var numPowerUpGenerated: Int = 0
    var context: ViewContext = ViewContext.getInstance()

    /**
     * Generates the powerups
     * @return LifePowerUp with an health increase
     */
    fun generate(): PowerUp {

        numPowerUpGenerated++
        val positionX :Float= Math.random().toFloat() * context.getWidthScreen()

        when(nextPowerUpType()) {
            PowerUpType.LIFE -> return LifePowerUp(
                positionX,
                0F,
                nextLifePowerUpIncreaseHelth() )
            PowerUpType.SHIELD -> return ShieldPowerUp(
                positionX,
                0F,
                nextShieldPowerUpDuration() )
            PowerUpType.GUNS -> return GunsPowerUp(
                positionX,
                0F,
                nextGunsPowerUpBulletType()
                )
        }
    }

    fun nextLifePowerUpIncreaseHelth():Float{
        return 500F + numPowerUpGenerated*10F
    }
    private fun nextShieldPowerUpDuration():Long{
        return 500 + numPowerUpGenerated.toLong()*10
    }
    private fun nextGunsPowerUpBulletType(): Weapon.BulletType{
        var improvment = if(numPowerUpGenerated < 20) 6*numPowerUpGenerated/20 else 6
        when(random.nextInt(1, 4+improvment)){
            1 -> return Weapon.BulletType.CLASSIC
            2,5 -> return Weapon.BulletType.GUST
            3,6,8 -> return Weapon.BulletType.MULTIPLE
            4,7,9 -> return Weapon.BulletType.LASER
            else -> return Weapon.BulletType.CLASSIC
        }
    }
    private fun nextPowerUpType(): PowerUpType{

        when(random.nextInt(0,3)) {
                0 -> return PowerUpType.LIFE
                1 -> return PowerUpType.GUNS
                2 -> return PowerUpType.SHIELD
                else -> return PowerUpType.LIFE
            }
        }
    }
