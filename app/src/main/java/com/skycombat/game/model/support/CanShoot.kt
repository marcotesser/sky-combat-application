package com.skycombat.game.model.support

import android.graphics.PointF;
import com.skycombat.game.model.Weapon
import com.skycombat.game.model.event.ShootListener
import com.skycombat.game.model.event.ShootObserver

interface CanShoot {

    var weapon:Weapon
    var shootObserver: ShootObserver

    fun startPointOfShoot():PointF

    fun shoot() {
        shootObserver.notify(weapon.generateBullet())
    }

    fun addOnShootListener(shootListener: ShootListener){
        shootObserver.attach(shootListener);
    }
}
