package com.skycombat.game.model.gui.element.ghost.strategy

import android.util.Log
import com.skycombat.game.model.gui.element.ghost.Ghost
import com.skycombat.game.model.gui.properties.AimToPositionX
import kotlin.math.abs

class LinearPositionStrategy() : AimedPositionStrategy {
    override fun move(entity: AimToPositionX) {
        if(abs(entity.getX() - entity.aimToPos() ) < entity.velocity()){
            entity.setX( entity.aimToPos() )
        } else if(entity.getX() > entity.aimToPos() ) {
            entity.setX( entity.getX() - entity.velocity() )
        } else {
            entity.setX( entity.getX() + entity.velocity() )
        }
    }
}