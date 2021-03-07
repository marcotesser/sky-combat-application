package com.skycombat.game.model.factory

import com.skycombat.game.GameView
import com.skycombat.game.model.Ghost

class GhostFactory(private val scene : GameView) {
    /**
     * Generates the enemies
     * @return Enemy to the scene
     * @see Player
     */
    fun generate(positionX : Float, positionY : Float, radius: Float, id: String) : Ghost {
        return Ghost(
            positionX,
            positionY,
            radius,
            id,
            scene
        )
    }
}