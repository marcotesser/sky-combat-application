package com.skycombat.game.model.gui.element.powerup

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.Weapon

class GunsPowerUp(x: Float, y: Float, var bulletType: Weapon.BulletType)
    : PowerUp(x,y) {

    override var powerUpImg: Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.gunpowerup)), RADIUS.toInt()*2, RADIUS.toInt()*2,false)

    override fun applyPowerUPEffects(player: Player){
        player.setBulletType(bulletType)
        this.used = true;
    }

}