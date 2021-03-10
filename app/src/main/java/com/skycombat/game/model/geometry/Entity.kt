package com.skycombat.game.model.geometry

interface Entity {
    fun collide(el : Rectangle) : Boolean
    fun collide(el : Circle) : Boolean
    fun collide(el : Entity): Boolean{
        return when (el) {
            is Circle -> collide(el)
            is Rectangle -> collide(el)
            else -> {
                //TODO(THROW EXCEPTION)
                false
            }
        }
    }
}