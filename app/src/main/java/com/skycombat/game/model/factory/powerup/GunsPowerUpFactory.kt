package com.skycombat.game.model.factory.powerup

import com.skycombat.game.model.factory.bullet.*
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.powerup.GunsPowerUp
import com.skycombat.game.model.gui.element.powerup.PowerUp
import kotlin.random.Random

class GunsPowerUpFactory : PowerUpFactory {
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
}