package com.skycombat.game.model.gui.event

class PlayerDeathObservable{
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