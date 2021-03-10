package com.skycombat.game.model.geometry

import android.graphics.PointF
import kotlin.math.pow
import kotlin.math.sqrt

interface Circle : Entity{
    fun getCenter() : PointF;
    fun getRadius() : Float;
    override fun collide(el : Rectangle) : Boolean{
        return el.collide(this)
    }
    override fun collide(el : Circle) : Boolean{
        val dist = sqrt((
                (getCenter().x - el.getCenter().x).pow(2) +
                        (getCenter().y - el.getCenter().y).pow(2)).toDouble())

        return dist < el.getRadius() + getRadius();
    }
}
