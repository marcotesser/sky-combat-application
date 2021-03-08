package com.skycombat.game.model.powerup

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.Player

class LifePowerUp(x: Float, y: Float, speed:Float, var healthIncrease: Float)
    :PowerUp(x,y,speed) {

    var paint = Paint()

    override fun draw(canvas : Canvas?){
        paint.color = Color.CYAN
        canvas?.drawCircle(x, y, PowerUp.RADIUS, paint)
    }

    override fun applyPowerUPEffects(player: Player){
        player.updateHealth(healthIncrease)
        this.used = true;
    }
}