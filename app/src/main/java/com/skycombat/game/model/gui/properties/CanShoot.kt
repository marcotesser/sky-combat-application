package com.skycombat.game.model.gui.properties

import android.graphics.PointF
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.event.ShootObservable
import com.skycombat.game.model.gui.event.ShootObserver

interface CanShoot {

    var weapon: Weapon
    var shootObservable: ShootObservable

    fun startPointOfShoot():PointF

    fun shoot() {
        shootObservable.notify(weapon.generateBullet())
    }

    fun addOnShootListener(shootObserver: ShootObserver){
        shootObservable.attach(shootObserver)
    }
}
