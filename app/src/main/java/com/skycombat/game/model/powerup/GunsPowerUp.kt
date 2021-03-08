package com.skycombat.game.model.powerup

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.Player
import com.skycombat.game.model.Weapon
import com.skycombat.game.model.bullet.Bullet

class GunsPowerUp(x: Float, y: Float, speed:Float, var bulletType: Weapon.BulletType)
    :PowerUp(x,y,speed) {

    var paint = Paint()

    override fun draw(canvas : Canvas?){
        paint.color = Color.CYAN
        canvas?.drawCircle(x, y, PowerUp.RADIUS, paint)
    }

    override fun applyPowerUPEffects(player: Player){
        player.setBulletType(bulletType)
        this.used = true;
    }

}