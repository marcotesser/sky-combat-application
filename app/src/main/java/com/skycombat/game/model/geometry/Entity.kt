package com.skycombat.game.model.geometry

interface Entity {
    fun collide(el : Rectangle) : Boolean
    fun collide(el : Circle) : Boolean
    fun collide(el : Entity): Boolean
}