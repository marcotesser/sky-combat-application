package com.skycombat.game.model.support

interface Entity {
    fun collide(el : Rectangle) : Boolean;
    fun collide(el : Circle) : Boolean;
}