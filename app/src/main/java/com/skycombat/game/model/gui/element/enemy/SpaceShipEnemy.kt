package com.skycombat.game.model.gui.element.enemy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.skycombat.R
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.element.enemy.movement.Movement

class SpaceShipEnemy(bulletType: Weapon.BulletType, mov : Movement, displayDimension: DisplayDimension) : Enemy(bulletType, mov, displayDimension) {

    companion object{
        const val MAX_HEALTH : Float = 400f
        const val WIDTH : Float = 300F
        const val HEIGHT : Float = 270F
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

    override var enemyImg = R.drawable.enemythree

    override fun getWidth(): Float {
        return WIDTH
    }
    override fun getHeight(): Float {
        return HEIGHT
    }
}