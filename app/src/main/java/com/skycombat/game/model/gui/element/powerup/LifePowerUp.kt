package com.skycombat.game.model.gui.element.powerup

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.model.gui.element.Player

class LifePowerUp(x: Float, y: Float, var healthIncrease: Float)
    : PowerUp(x,y) {

    override var powerUpImg: Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.lifepowerup)), RADIUS.toInt()*2, RADIUS.toInt()*2,false)

    override fun applyPowerUPEffects(player: Player){
        player.updateHealth(healthIncrease)
        this.used = true;
    }
}