package com.skycombat.game.model.gui.element.enemy

import com.skycombat.R
import com.skycombat.game.model.factory.bullet.BulletFactory
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.enemy.movement.Movement

class JetEnemy(bulletFactory: BulletFactory, mov : Movement, displayDimension: DisplayDimension) : Enemy(bulletFactory, mov, displayDimension) {

    companion object{
        const val MAX_HEALTH : Float = 300f
        const val WIDTH : Float = 250F
        const val HEIGHT : Float = 200F
    }
    override var enemyImg : Int = R.drawable.enemytwo

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

    override fun getWidth(): Float {
        return WIDTH
    }
    override fun getHeight(): Float {
        return HEIGHT
    }
}