package com.skycombat.game.model.gui.element.ghost.strategy

import com.skycombat.game.model.gui.element.ghost.Ghost

interface AimedPositionStrategy {
    fun move(ghost: Ghost)
}