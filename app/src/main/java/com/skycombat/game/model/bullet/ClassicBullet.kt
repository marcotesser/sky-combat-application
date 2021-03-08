package com.skycombat.game.model.bullet

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import com.skycombat.game.model.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.support.Circle

class ClassicBullet(x : Float, y : Float, collisionStrategy: CollisionStrategy)
    : Bullet(x,y, collisionStrategy), Circle{

    companion object {
        const val RADIUS: Float = 10.0F
        const val DAMAGE: Float = 2.0F
        const val SPEED: Float = 4.0F
    }
    /**
     * Draws the bullet
     * @param canvas : the canvas onto which the bullet will be drawn
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(x, y , RADIUS , paint)
    }

    init {
        paint.color = if( target == CollisionStrategy.Target.PLAYER) Color.RED else Color.GREEN
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