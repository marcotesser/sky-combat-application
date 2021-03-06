package com.skycombat.game.model.support

import android.graphics.RectF

interface Rectangle : Entity{
    fun getPosition() : RectF;
    override fun collide(el : Rectangle) : Boolean{
        return getPosition().left < getPosition().right && getPosition().right > getPosition().left &&
                getPosition().top > getPosition().bottom && getPosition().bottom < getPosition().top
    }
    override fun collide(el : Circle) : Boolean{
        return el.collide(this)
    }
}