package com.skycombat.game.model.gui.element.enemy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.skycombat.R
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.element.enemy.movement.Movement

class JetEnemy(bulletType: Weapon.BulletType, mov : Movement) : Enemy(bulletType, mov) {

    companion object{
        const val MAX_HEALTH : Float = 300f
        const val WIDTH : Float = 250F
        const val HEIGHT : Float = 220F
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

    override var enemyImg : Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.enemytwo)), WIDTH.toInt(), HEIGHT.toInt(),false)

    override fun getWidth(): Float {
        return WIDTH
    }
    override fun getHeight(): Float {
        return HEIGHT
    }
}