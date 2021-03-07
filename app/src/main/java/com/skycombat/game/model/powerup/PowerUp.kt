package com.skycombat.game.model.powerup

import com.skycombat.game.model.Player
import com.skycombat.game.model.support.Circle
import com.skycombat.game.model.support.GUIElement

interface PowerUp : Circle, GUIElement {
    companion object {
        val RADIUS: Float = 20f;
    }
    fun apply(player : Player)
    fun isUsed() : Boolean
}