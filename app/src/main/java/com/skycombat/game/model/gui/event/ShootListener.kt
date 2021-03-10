package com.skycombat.game.model.gui.event

import com.skycombat.game.model.gui.element.bullet.Bullet

interface ShootListener {
    fun onShoot(bullet: Bullet)
}