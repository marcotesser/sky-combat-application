package com.skycombat.game.model.powerup

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.Player
import com.skycombat.game.model.Weapon
import com.skycombat.game.model.bullet.Bullet

class GunsPowerUp(x: Float, y: Float, var bulletType: Weapon.BulletType)
    :PowerUp(x,y) {

    override var powerUpImg: Bitmap = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.gunpowerup)),RADIUS.toInt()*2,RADIUS.toInt()*2,false)

    override fun applyPowerUPEffects(player: Player){
        player.setBulletType(bulletType)
        this.used = true;
    }

}