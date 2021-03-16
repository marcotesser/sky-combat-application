package com.skycombat.game.model.gui.element.powerup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.skycombat.R
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.element.Player

class GunsPowerUp(x: Float, y: Float, var bulletType: Weapon.BulletType, val dimension: DisplayDimension)
    : PowerUp(x,y, dimension) {

    override var powerUpImg = R.drawable.gunpowerup

    override fun applyPowerUPEffects(player: Player){
        player.setBulletType(bulletType)
        this.used = true
    }

}