package com.skycombat.game.model.gui.element.enemy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.skycombat.R
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.element.enemy.movement.Movement

class PlaneEnemy(bulletType: Weapon.BulletType, mov : Movement) : Enemy(bulletType,mov) {

    companion object{
        const val MAX_HEALTH : Float = 200f
        const val WIDTH : Float = 200F
        const val HEIGHT : Float = 180F
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

    override var enemyImg : Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.enemyone)), WIDTH.toInt(), HEIGHT.toInt(),false)

    override fun getWidth(): Float {
        return WIDTH
    }
    override fun getHeight(): Float {
        return HEIGHT
    }
}