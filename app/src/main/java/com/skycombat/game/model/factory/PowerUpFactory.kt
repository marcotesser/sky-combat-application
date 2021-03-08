package com.skycombat.game.model.factory

import com.skycombat.game.GameView
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.Weapon
import com.skycombat.game.model.powerup.GunsPowerUp
import com.skycombat.game.model.powerup.LifePowerUp
import com.skycombat.game.model.powerup.PowerUp
import com.skycombat.game.model.powerup.ShieldPowerUp

/**
 * Represents a Life PowerUp Factory
 * @param scene : the gameview onto which the life powerups will be spawned
 */
class PowerUpFactory(var seed:Long) {

    enum class PowerUpType {
        LIFE, SHIELD, GUNS
    }

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
                nextPowerUpSpeed(),
                nextLifePowerUpIncreaseHelth() )
            PowerUpType.SHIELD -> return ShieldPowerUp(
                positionX,
                0F,
                nextPowerUpSpeed(),
                nextShieldPowerUpDuration() )
            PowerUpType.GUNS -> return GunsPowerUp(
                positionX,
                0F,
                nextPowerUpSpeed(),
                nextGunsPowerUpBulletType()
                )
        }
    }

    private fun nextPowerUpSpeed(): Float{
        // TODO(in funzione di numPowerUpGenerated)
        return 8F
    }
    private fun nextLifePowerUpIncreaseHelth():Float{
        // TODO(in funzione di numPowerUpGenerated)
        return 100F
    }
    private fun nextShieldPowerUpDuration():Long{
        // TODO(in funzione di numPowerUpGenerated)
        return 500
    }
    private fun nextGunsPowerUpBulletType():Weapon.BulletType{
        // TODO(in funzione di numPowerUpGenerated)
        return Weapon.BulletType.CLASSIC
    }
    private fun nextPowerUpType(): PowerUpType{
        // TODO(In funzione del seed e di numPowerUpGenerated)
        return PowerUpType.LIFE
    }

    /*
    fun generateRandom(): PowerUp{
        var allTypes = PowerUpType.values()
        return generate(allTypes.elementAt((Math.random()*100).toInt()%allTypes.size))
    }
    */

}