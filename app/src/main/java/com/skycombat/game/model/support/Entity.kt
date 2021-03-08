package com.skycombat.game.model.support

interface Entity {
    fun collide(el : Rectangle) : Boolean;
    fun collide(el : Circle) : Boolean

    fun collide(el : Entity): Boolean{
        if(el is Circle) return collide(el)
        else if(el is Rectangle) return collide(el)
        else{
            //TODO(THROW EXCEPTION
            return false
        }
    }
}