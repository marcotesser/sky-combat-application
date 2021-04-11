package com.skycombat.game.model.gui.event

fun interface PlayerDeathObserver {
    fun onDeathOccur(time: Long)
}