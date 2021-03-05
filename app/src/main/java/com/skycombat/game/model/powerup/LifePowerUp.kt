package com.skycombat.game.model.powerup

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.skycombat.game.model.Player

class LifePowerUp(var healthIncrease : Long, var x : Float, var y: Float) : PowerUp {
    val DY : Int = 5
    var used : Boolean = false
    override fun draw(canvas : Canvas?){
        val paint = Paint()
        paint.setColor(Color.CYAN)
        canvas?.drawCircle(x, y, PowerUp.RADIUS, paint)
    }
    override fun update(){
        this.y += DY
    }
    override fun apply(player: Player) {
        this.used = true;
        player.health = Player.MAX_HEALTH.coerceAtMost(player.health + healthIncrease)
    }

    override fun getX(): Float {
        return x
    }

    override fun getY(): Float {
        return y
    }

    override fun isUsed(): Boolean {
        return used
    }
}