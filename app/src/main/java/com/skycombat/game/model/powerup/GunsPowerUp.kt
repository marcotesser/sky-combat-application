package com.skycombat.game.model.powerup

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.Player

class GunsPowerUp(x: Float, y: Float, speed:Float)
    :PowerUp(x,y,speed) {

    var paint = Paint()

    override fun draw(canvas : Canvas?){
        paint.color = Color.CYAN
        canvas?.drawCircle(x, y, PowerUp.RADIUS, paint)
    }

    override fun applyCollisionEffects(entityHitted: HasHealth){
        if(entityHitted is Player) {
            //TODO(Settaggio della nuova arma passata con un nuovo parametro)
            this.used = true;
        }
    }

}