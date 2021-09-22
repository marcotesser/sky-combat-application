package com.skycombat.game.model.gui.element.enemy

import com.skycombat.R
import com.skycombat.game.model.factory.bullet.BulletFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.enemy.movement.Movement

class SpaceShipEnemy(bulletFactory: BulletFactory, mov : Movement, displayDimension: DisplayDimension) : Enemy(bulletFactory, mov, displayDimension) {

    companion object{
        const val MAX_HEALTH : Float = 400f
        const val WIDTH : Float = 250F
        const val HEIGHT : Float = 250F
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

    override var enemyImg : Int = R.drawable.enemythree

    override fun getWidth(): Float {
        return WIDTH
    }
    override fun getHeight(): Float {
        return HEIGHT
    }
}