package com.skycombat.game.model.gui.element.powerup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.skycombat.R
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.Player

class ShieldPowerUp(x: Float, y: Float, private var duration: Long, val dimension: DisplayDimension)
    : PowerUp(x,y, dimension) {

    override var powerUpImg = R.drawable.shieldpowerup

    override fun applyPowerUPEffects(player: Player){
        player.applyShield(duration)
        this.used = true
    }
}