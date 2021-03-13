package com.skycombat.game.model.gui.properties

import com.skycombat.game.model.gui.component.HealthBar

interface HasHealth {

    var health : Float

    fun updateHealth(delta: Float) {
        if(isDamageable() || delta>0) {
            health = when {
                this.getCurrentHealth() + delta < 0 -> 0F
                this.getCurrentHealth() + delta > this.getMaxHealth() -> this.getMaxHealth()
                else -> (getCurrentHealth() + delta)
            }
        }
    }
    fun isDamageable():Boolean = true
    fun getMaxHealth() : Float

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

    fun isAlive() : Boolean{
        return this.health > 0
    }

    /**
     * Checks if the enemy is dead
     * @see HealthBar
     */
    fun kill(){
        this.health = 0f
    }

}