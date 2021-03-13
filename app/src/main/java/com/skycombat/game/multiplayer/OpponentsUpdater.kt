package com.skycombat.game.multiplayer

import com.skycombat.game.model.gui.element.ghost.Ghost

abstract class OpponentsUpdater : Thread(){
    abstract fun getOpponents() : List<Ghost>;
    abstract fun stopUpdates()
}