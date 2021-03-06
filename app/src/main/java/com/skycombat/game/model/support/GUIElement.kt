package com.skycombat.game.model.support

import android.graphics.Canvas
import com.skycombat.game.GameView

interface GUIElement {
    fun draw(canvas: Canvas?);
    fun update();
    fun shouldRemove() : Boolean;
}