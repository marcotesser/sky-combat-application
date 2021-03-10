package com.skycombat.game.model.gui.event

import com.skycombat.game.model.gui.element.bullet.Bullet

class ShootObservable(){
    var shootObservers:ArrayList<ShootObserver> = ArrayList()
    fun attach(shootObserver: ShootObserver){
        shootObservers.add(shootObserver)
    }
    fun detach(shootObserver: ShootObserver){
        shootObservers.remove(shootObserver)
    }
    fun notify(bullet: Bullet){
        shootObservers.forEach{
            el -> el.onShoot(bullet)
        }
    }
}