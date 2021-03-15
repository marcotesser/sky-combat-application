package com.skycombat.game.model.gui.event

import com.skycombat.game.model.gui.element.bullet.Bullet

class PlayerDeathObservable(){
    var shootObservers:ArrayList<PlayerDeathObserver> = ArrayList()
    fun attach(playerDeathObserver: PlayerDeathObserver){
        shootObservers.add(playerDeathObserver)
    }
    fun detach(playerDeathObserver: PlayerDeathObserver){
        shootObservers.remove(playerDeathObserver)
    }
    fun notify(time: Long){
        shootObservers.forEach{
            el -> el.onDeathOccur(time)
        }
    }
}