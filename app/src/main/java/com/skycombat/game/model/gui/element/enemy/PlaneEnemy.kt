package com.skycombat.game.model.gui.element.enemy

import com.skycombat.R
import com.skycombat.game.model.factory.bullet.BulletFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.enemy.movement.Movement

/**
 * @param healthCoefficient : coefficiente di aumento della vita ->
 *      > 1 -> aumenta la vita
 *      < 1 -> diminuisce la vita
 *      = 1 -> la vita non cambia
 */
class PlaneEnemy(bulletFactory: BulletFactory, mov : Movement, displayDimension: DisplayDimension, var healthCoefficient : Float = 1F) : Enemy(bulletFactory, mov, displayDimension) {

    companion object{
        const val MAX_HEALTH : Float = 200f
        const val WIDTH : Float = 200F
        const val HEIGHT : Float = 180F
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH * healthCoefficient
    }

    override var enemyImg : Int = R.drawable.enemyone

    override fun getWidth(): Float {
        return WIDTH
    }
    override fun getHeight(): Float {
        return HEIGHT
    }
}