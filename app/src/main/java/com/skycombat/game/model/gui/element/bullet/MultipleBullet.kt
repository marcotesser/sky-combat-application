package com.skycombat.game.model.gui.element.bullet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import com.skycombat.R
import com.skycombat.game.model.geometry.Rectangle
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.DrawVisitor
import com.skycombat.game.model.gui.element.bullet.strategy.CollisionStrategy

class MultipleBullet (left : Float,top : Float, collisionStrategy: CollisionStrategy, direction: Direction, val dimension: DisplayDimension)
    : Bullet(left+ WIDTH /2F,top+ HEIGHT /2F, collisionStrategy, direction, dimension), Rectangle{

    companion object {
        const val DAMAGE: Float = 50.0F
        const val SPEED: Float = 50.0F
        const val WIDTH: Float = 140F
        const val HEIGHT: Float = 40F
    }

    var bulletImg = R.drawable.bullet3

    /**
     * Draws the bullet
     * @param canvas : the canvas onto which the bullet will be drawn
     */
    override fun draw(canvas: Canvas?, visitor: DrawVisitor) {
        visitor.draw(canvas, this)
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