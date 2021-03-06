package com.skycombat.game.model.powerup

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import com.skycombat.game.GameView
import com.skycombat.game.model.Player

class LifePowerUp(var healthIncrease : Long, var x : Float, var y: Float) : PowerUp {
    val DY : Int = 5
    var used : Boolean = false
    override fun draw(canvas : Canvas?){
        val paint = Paint()
        paint.setColor(Color.CYAN)
        canvas?.drawCircle(x, y, PowerUp.RADIUS, paint)
    }

    override fun shouldRemove(): Boolean {
        return this.used
    }

    override fun getCenter(): PointF {
        return PointF(this.x, this.y)
    }

    override fun getRadius(): Float {
        return PowerUp.RADIUS
    }

    override fun update(){
        this.y += DY
    }
    override fun apply(player: Player) {
        this.used = true;
        player.updateHealth(this.healthIncrease.toFloat())
    }

    override fun isUsed(): Boolean {
        return used
    }
}