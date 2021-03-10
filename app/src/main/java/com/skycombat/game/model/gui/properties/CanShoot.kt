package com.skycombat.game.model.gui.properties

import android.graphics.PointF;
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.event.ShootListener
import com.skycombat.game.model.gui.event.ShootObserver

interface CanShoot {

    var weapon: Weapon
    var shootObserver: ShootObserver

    fun startPointOfShoot():PointF

    fun shoot() {
        shootObserver.notify(weapon.generateBullet())
    }

    fun addOnShootListener(shootListener: ShootListener){
        shootObserver.attach(shootListener);
    }
}
