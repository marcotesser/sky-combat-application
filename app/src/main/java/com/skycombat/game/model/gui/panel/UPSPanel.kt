package com.skycombat.game.model.gui.panel

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.skycombat.game.scene.GameLoop
import com.skycombat.game.scene.GameView

class UPSPanel(var x: Float, var y: Float, var gameLoop : GameLoop, var scene : GameView) :
        GamePanel {
    override fun draw(canvas: Canvas?) {
        val avg: String = gameLoop.getAverageUPS().toInt().toString();
        val paint = Paint()
        paint.color = Color.RED
        paint.textSize = 50F;
        canvas?.drawText("UPS: $avg", x, y, paint)
    }

}