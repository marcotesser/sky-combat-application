package com.skycombat.game.model

interface HasHealth {
    fun getCurrentHealth() : Float;
    fun getMaxHealth() : Float;
}