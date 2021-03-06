package com.skycombat.game.model.support

interface GUIElement : Drawable, Updatable {
    fun shouldRemove() : Boolean;
}