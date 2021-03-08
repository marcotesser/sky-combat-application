package com.skycombat.game.model

import android.util.Log
import com.skycombat.game.model.component.HealthBar

interface HasHealth {

    var health : Float

    fun updateHealth(delta: Float) {
        if(isDamageable() || delta>0)
        when {
            this.getCurrentHealth() + delta < 0 -> health = 0F;
            this.getCurrentHealth() + delta > this.getMaxHealth() -> health = this.getMaxHealth();
            else -> health = (getCurrentHealth() + delta)
        }
    }
    fun isDamageable():Boolean = true
    fun getMaxHealth() : Float;

    fun getCurrentHealth() : Float{
        return health
    }

    /**
     * Checks if the enemy is dead
     * @see HealthBar
     */
    fun isDead() : Boolean{
        return this.health <= 0
    }
}