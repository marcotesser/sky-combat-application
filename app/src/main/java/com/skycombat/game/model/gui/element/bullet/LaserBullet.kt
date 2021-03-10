package com.skycombat.game.model.gui.element.bullet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import com.skycombat.R
import com.skycombat.game.model.geometry.Rectangle
import com.skycombat.game.model.gui.element.bullet.strategy.CollisionStrategy

class LaserBullet(left : Float,top : Float, collisionStrategy: CollisionStrategy, direction: Direction)
: Bullet(left+ WIDTH /2F,top+ HEIGHT /2F, collisionStrategy, direction), Rectangle{

    companion object {
        const val DAMAGE: Float = 80.0F
        const val SPEED: Float = 80.0F
        const val WIDTH: Float = 30F
        const val HEIGHT: Float = 400F
    }

    var bulletImg: Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.laser)), WIDTH.toInt(), HEIGHT.toInt(),false)

    /**
     * Draws the bullet
     * @param canvas : the canvas onto which the bullet will be drawn
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(bulletImg,x- WIDTH /2F,y- HEIGHT /2F,null)
    }

    override fun getDamage():Float{
        return DAMAGE
    }
    override fun getSpeed():Float{
        return SPEED
    }

    override fun getPosition(): RectF {
        return RectF(x- WIDTH /2F, y- HEIGHT /2F, x+ WIDTH /2F , y+ HEIGHT /2F)
    }

}