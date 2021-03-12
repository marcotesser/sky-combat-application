package com.skycombat.game.model.gui.element.ghost.strategy

import com.skycombat.game.model.gui.element.ghost.Ghost
import com.skycombat.game.model.gui.properties.AimToPositionX

interface AimedPositionStrategy {
    fun move(entity : AimToPositionX)
}