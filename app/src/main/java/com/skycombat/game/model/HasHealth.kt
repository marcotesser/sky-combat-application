package com.skycombat.game.model

import android.util.Log

interface HasHealth {
    fun updateHealth(delta: Float) {
        if(isDamageable() || delta>0)
        when {
            this.getCurrentHealth() + delta < 0 -> this.setHealth(0f);
            this.getCurrentHealth() + delta > this.getMaxHealth() -> this.setHealth(this.getMaxHealth());
            else -> this.setHealth(getCurrentHealth() + delta)
        }
    }
    fun isDamageable():Boolean =true;
    fun getCurrentHealth() : Float;
    fun setHealth(health: Float);
    fun getMaxHealth() : Float;
}