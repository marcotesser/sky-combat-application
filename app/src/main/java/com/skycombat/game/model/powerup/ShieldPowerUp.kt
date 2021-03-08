package com.skycombat.game.model.powerup

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.Player

class ShieldPowerUp(x: Float, y: Float, speed:Float, var duration: Long)
    :PowerUp(x,y,speed) {

    override var powerUpImg: Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.shieldpowerup)),RADIUS.toInt()*2,RADIUS.toInt()*2,false)

    override fun applyPowerUPEffects(player: Player){
        player.applyShield(duration)
        this.used = true;
    }

    }