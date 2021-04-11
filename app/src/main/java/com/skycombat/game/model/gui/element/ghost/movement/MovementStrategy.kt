package com.skycombat.game.model.gui.element.ghost.movement

import com.skycombat.game.model.gui.properties.AimToPositionX

interface MovementStrategy {
    fun move(entity : AimToPositionX)
}