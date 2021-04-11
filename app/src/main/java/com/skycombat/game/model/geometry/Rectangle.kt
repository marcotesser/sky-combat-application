package com.skycombat.game.model.geometry

import android.graphics.RectF

interface Rectangle : Entity{

    companion object{
        private fun collidePartial(rc: RectF, el : RectF):Boolean{
            return (((rc.left < el.right && rc.left > el.left)|| (el.left < rc.right && el.right > rc.right))
                    && ((rc.top < el.bottom && rc.top > el.top)|| (el.top < rc.bottom && el.bottom > rc.bottom)))
        }
    }

    fun getPosition() : RectF
    override fun collide(el : Rectangle) : Boolean{
        return collidePartial(this.getPosition(),el.getPosition())||collidePartial(el.getPosition(),this.getPosition())
    }
    override fun collide(el : Circle) : Boolean{
        val rect = RectF(el.getCenter().x-el.getRadius(), el.getCenter().y-el.getRadius(),
            el.getCenter().x+el.getRadius(), el.getCenter().y+el.getRadius())
        return collidePartial(this.getPosition(),rect)||collidePartial(rect,this.getPosition())
    }
    override fun collide(el: Entity): Boolean {
        return el.collide(this)
    }
}