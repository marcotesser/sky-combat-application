package com.skycombat.game.model.bullet

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import com.skycombat.game.model.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.support.Rectangle

class LaserBullet(var left : Float,var top : Float, collisionStrategy: CollisionStrategy)
: Bullet(left+WIDTH/2F,top+HEIGHT/2, collisionStrategy), Rectangle{

    companion object {
        const val DAMAGE: Float = 2.0F
        const val SPEED: Float = 7.0F
        const val WIDTH: Float = 0.5F
        const val HEIGHT: Float = 30F
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(RectF(left, top, left + WIDTH, top+HEIGHT), paint)
    }

    init {
        paint.color = if( target == CollisionStrategy.Target.PLAYER) Color.MAGENTA else Color.YELLOW
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