package com.skycombat.game.model.enemy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import com.skycombat.R
import com.skycombat.game.model.Weapon
import com.skycombat.game.model.enemy.movement.Movement

class EnemyOne(left : Float, top : Float, bulletType: Weapon.BulletType,movement : Movement) : Enemy(left,top,bulletType,movement) {

    companion object{
        val MAX_HEALTH : Float = 200f
        val WIDTH : Float = 230F
        val HEIGHT : Float = 200F
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

    override var enemyImg : Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.enemyone)),WIDTH.toInt(),HEIGHT.toInt(),false)

    override fun MovHandle(){
        val randleft = left + (Math.random() * (horizontalAttitude * 10)).toFloat()
        val randtop = top + (Math.random() * (verticalAttitude * 10)).toFloat()
        if (randleft + getWidth() > context.getWidthScreen()) {
            horizontalAttitude = -1
        } else if (randleft < 0) {
            horizontalAttitude = 1
        }
        if (randtop + getHeight() > context.getHeightScreen() * 0.3) {
            verticalAttitude = -1
        } else if (randtop < 0) {
            verticalAttitude = 1
        }
        left = randleft
        top = randtop
    }

    override fun getWidth(): Float {
        return WIDTH
    }
    override fun getHeight(): Float {
        return HEIGHT
    }
}