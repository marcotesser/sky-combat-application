package com.skycombat.game.model.support

import android.graphics.PointF
import com.skycombat.game.model.Bullet
import kotlin.math.pow
import kotlin.math.sqrt

interface Circle : Entity{
    fun getCenter() : PointF;
    fun getRadius() : Float;
    override fun collide(el : Rectangle) : Boolean{
        val op = el.getPosition()
        return getCenter().y+ getRadius() < op.top+(op.height()) &&
                getCenter().y - getRadius() > op.top &&
                getCenter().x > op.left &&
                getCenter().x < op.right
    }
    override fun collide(el : Circle) : Boolean{
        val dist = sqrt((
                (getCenter().x - el.getCenter().x).pow(2) +
                        (getCenter().y - el.getCenter().y).pow(2)).toDouble())

        return dist < el.getRadius() + getRadius();
    }
}
