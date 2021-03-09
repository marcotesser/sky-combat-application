package com.skycombat.game.panel

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.skycombat.game.GameLoop
import com.skycombat.game.gameview.GameView
import com.skycombat.game.model.ViewContext

class FPSPanel(var x: Float, var y: Float, var gameLoop : GameLoop, var scene : GameView) : GamePanel{
    var context: ViewContext = ViewContext.getInstance()
    override fun draw(canvas: Canvas?) {
        val avg: String = gameLoop.getAverageFPS().toInt().toString();
        val paint = Paint()
        paint.color = Color.RED
        paint.textSize = 50F;
        canvas?.drawText("FPS: $avg", x, y, paint)
    }

}