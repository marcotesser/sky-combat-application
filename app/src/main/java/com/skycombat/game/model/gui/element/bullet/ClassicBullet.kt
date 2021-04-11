package com.skycombat.game.model.gui.element.bullet

import android.graphics.Canvas
import android.graphics.PointF
import com.skycombat.R
import com.skycombat.game.model.geometry.Circle
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.DrawVisitor
import com.skycombat.game.model.gui.element.bullet.collision.CollisionStrategy

class ClassicBullet(x : Float, y : Float, collisionStrategy: CollisionStrategy, direction: Direction, val dimension: DisplayDimension)
    : Bullet(x,y, collisionStrategy, direction, dimension), Circle{

    companion object {
        const val RADIUS: Float = 20.0F
        const val DAMAGE: Float = 50.0F
        const val SPEED: Float = 45.0F
    }

    var bulletImg: Int = R.drawable.bullet2

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

    override fun getRadius(): Float {
        return RADIUS
    }
    override fun getCenter(): PointF {
        return PointF(this.x, this.y)
    }
}