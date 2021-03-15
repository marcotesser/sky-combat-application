package com.skycombat.game.model.gui.event

import com.skycombat.game.model.gui.element.bullet.Bullet

fun interface PlayerDeathObserver {
    fun onDeathOccur(time: Long)
}