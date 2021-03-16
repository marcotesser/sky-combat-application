package com.skycombat.game.model.gui

import android.graphics.Canvas

interface Drawable {
    fun draw(canvas: Canvas?, visitor: DrawVisitor)
}