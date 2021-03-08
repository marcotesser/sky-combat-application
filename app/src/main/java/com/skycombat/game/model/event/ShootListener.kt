package com.skycombat.game.model.event

import com.skycombat.game.model.bullet.Bullet

interface ShootListener {
    fun onShoot(bullet: Bullet)
}