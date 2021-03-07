package com.skycombat.game.model.support

import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.support.GUIElement

interface CollisionParticle : GUIElement {

    fun applyCollisionEffects(entityHitted: HasHealth)
}