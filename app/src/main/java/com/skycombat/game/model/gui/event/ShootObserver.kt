package com.skycombat.game.model.gui.event

import com.skycombat.game.model.gui.element.bullet.Bullet

fun interface ShootObserver {
    fun onShoot(bullet: Bullet)
}