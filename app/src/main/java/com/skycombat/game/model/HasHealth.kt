package com.skycombat.game.model

import android.util.Log

interface HasHealth {
    fun updateHealth(delta: Float) {
        if( this.getCurrentHealth() + delta < 0){
            this.setHealth(0f);
        } else if(this.getCurrentHealth() + delta > this.getMaxHealth()){
            this.setHealth(this.getMaxHealth());
        } else {
            this.setHealth(getCurrentHealth() + delta)
        }
    }
    fun getCurrentHealth() : Float;
    fun setHealth(health: Float);
    fun getMaxHealth() : Float;
}