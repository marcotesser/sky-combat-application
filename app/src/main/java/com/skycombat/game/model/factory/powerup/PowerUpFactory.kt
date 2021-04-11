package com.skycombat.game.model.factory.powerup

import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.powerup.PowerUp
import kotlin.random.Random

interface PowerUpFactory {
    fun generate(x: Float, iteration: Int, random: Random, displayDimension: DisplayDimension) : PowerUp
}