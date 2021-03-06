package com.skycombat.game.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.skycombat.game.GameView

class LifePowerUp (x : Float,
                   y : Float,
                   radius:Float,
                   speed:Float,
                   scene: GameView,
                   var healthIncrease:Float = 0F
) : AbstParticle(x,y,radius,speed,scene){

    override fun draw(canvas : Canvas?){
        val paint = Paint()
        paint.setColor(Color.CYAN)
        canvas?.drawCircle(positionX, positionY, RADIUS, paint)
    }

    override fun handleHit(player: AbstEntity) {
        setHit();
        player.addDeltaHealth(healthIncrease);
    }


}