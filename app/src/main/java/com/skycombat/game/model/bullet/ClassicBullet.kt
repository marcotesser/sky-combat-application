package com.skycombat.game.model.bullet

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.model.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.support.Circle

class ClassicBullet(x : Float, y : Float, collisionStrategy: CollisionStrategy)
    : Bullet(x,y, collisionStrategy), Circle{

    companion object {
        const val RADIUS: Float = 15.0F
        const val DAMAGE: Float = 50.0F
        const val SPEED: Float = 20.0F
    }

    var bulletImg: Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet2)),getRadius().toInt()*2,getRadius().toInt()*2,false)

    /**
     * Draws the bullet
     * @param canvas : the canvas onto which the bullet will be drawn
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(bulletImg,x-getRadius(),y-getRadius(),null)
    }

    override fun getDamage():Float{
        return DAMAGE
    }
    override fun getSpeed():Float{
        return SPEED
    }

    override fun getRadius(): Float {
        return RADIUS
    }
    override fun getCenter(): PointF {
        return PointF(this.x, this.y)
    }
}