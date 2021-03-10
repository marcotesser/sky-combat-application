package com.skycombat.game.model.gui.element.powerup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.skycombat.R
import com.skycombat.game.model.gui.element.Player

class ShieldPowerUp(x: Float, y: Float, private var duration: Long)
    : PowerUp(x,y) {

    override var powerUpImg: Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.shieldpowerup)), RADIUS.toInt()*2, RADIUS.toInt()*2,false)

    override fun applyPowerUPEffects(player: Player){
        player.applyShield(duration)
        this.used = true
    }
}