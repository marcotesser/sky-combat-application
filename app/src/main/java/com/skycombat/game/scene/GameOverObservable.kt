package com.skycombat.game.scene;

import java.util.*

class GameOverObservable(){
    var gameOverObservers:ArrayList<GameOverObserver> = ArrayList()
    fun attach(gameOverObservable: GameOverObserver){
        gameOverObservers.add(gameOverObservable)
    }
    fun detach(shootObserver: GameOverObserver){
        gameOverObservers.remove(shootObserver)
    }
    fun notify(score : Long){
        gameOverObservers.forEach{
            el -> el.gameOver(score)
        }
    }
}
