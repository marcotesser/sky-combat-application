package com.skycombat.game.model.gui.element.ghost.strategy

import android.util.Log
import com.skycombat.game.model.gui.element.ghost.Ghost
import kotlin.math.abs

class LinearPositionStrategy() : AimedPositionStrategy {
    companion object{
        const val MAX_DELTA = 3f;
    }
    override fun move(ghost: Ghost) {
        if(abs(ghost.x - ghost.aimedPositionX ) < MAX_DELTA){
            ghost.x = ghost.aimedPositionX
        } else if(ghost.x > ghost.aimedPositionX ) {
            ghost.x -= MAX_DELTA
        } else {
            ghost.x += MAX_DELTA
        }
    }
}