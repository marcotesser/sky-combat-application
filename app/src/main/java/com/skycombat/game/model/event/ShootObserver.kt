package com.skycombat.game.model.event

import com.skycombat.game.model.bullet.Bullet

class ShootObserver(){
    var shootListeners:ArrayList<ShootListener> = ArrayList()
    fun attach(shootListener: ShootListener){
        shootListeners.add(shootListener)
    }
    fun detach(shootListener: ShootListener){
        shootListeners.remove(shootListener)
    }
    fun notify(bullet: Bullet){
        shootListeners.forEach{
            el -> el.onShoot(bullet)
        }
    }
}