package com.skycombat.game.model.bullet

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.model.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.support.Rectangle

class LaserBullet(var left : Float,var top : Float, collisionStrategy: CollisionStrategy)
: Bullet(left+WIDTH/2F,top+HEIGHT/2, collisionStrategy), Rectangle{

    companion object {
        const val DAMAGE: Float = 70.0F
        const val SPEED: Float = 8.0F
        const val WIDTH: Float = 30F
        const val HEIGHT: Float = 200F
    }

    var bulletImg: Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.laser)), WIDTH.toInt(),HEIGHT.toInt(),false)

    /**
     * Draws the bullet
     * @param canvas : the canvas onto which the bullet will be drawn
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(bulletImg,x,y,null)
    }

    override fun getDamage():Float{
        return DAMAGE
    }
    override fun getSpeed():Float{
        return SPEED
    }

    override fun getPosition(): RectF {
        return RectF(left, this.top, this.left + WIDTH , this.top + HEIGHT)
    }

}