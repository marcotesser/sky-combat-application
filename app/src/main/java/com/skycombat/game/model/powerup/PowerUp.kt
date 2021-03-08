package com.skycombat.game.model.powerup

import android.graphics.PointF
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.support.Circle
import com.skycombat.game.model.support.CollisionParticle

abstract class PowerUp(var x : Float, var y : Float, var speed:Float)
    : Circle, CollisionParticle {

    companion object {
        val RADIUS: Float = 20f;
    }

    val context: ViewContext = ViewContext.getInstance()
    var used : Boolean = false

    override fun update(){
        this.y += speed
    }

    override fun shouldRemove(): Boolean {
        return this.used || this.y < 0 || this.y > context.getHeightScreen()
    }

    override fun getCenter(): PointF {
        return PointF(this.x, this.y)
    }
    override fun getRadius(): Float {
        return RADIUS
    }
}