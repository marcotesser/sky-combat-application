package com.skycombat.game.model.gui.element

import com.skycombat.game.model.gui.Drawable
import com.skycombat.game.model.gui.Updatable

interface GUIElement : Drawable, Updatable {
    fun shouldRemove() : Boolean;
}