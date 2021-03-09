package com.skycombat.game.model

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.GameSession
import com.skycombat.game.model.support.Circle
import com.skycombat.game.model.support.GUIElement
import java.util.*

class Ghost(var id: String) :
    Circle, GUIElement {
    companion object{
        var RADIUS: Float = 90F
    }

    var paint = Paint()
    var positionX:Float
    var positionY:Float
    var ghostImg : Bitmap
    var context: ViewContext = ViewContext.getInstance()

    var stillInGame : Boolean = true

    init {
        paint.setAlpha(50)
        positionX=context.getWidthScreen()/2F
        positionY= context.getHeightScreen()/ 5 * 4
        ghostImg = Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.player)),
            RADIUS.toInt()*2,
            RADIUS.toInt()*2,false)
    }



    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(ghostImg,positionX- Player.RADIUS /2,positionY- Player.RADIUS /2,paint)
    }

    override fun update() {
        var stillInGameChecker: Boolean = false
        var x:Float;var y:Float
        GameSession.otherPlayers?.forEach { el ->
            if(el.id == id) {
                x = (el.positionX * context.getWidthScreen()).toFloat()
                y = (el.positionY * context.getHeightScreen()).toFloat()
                setPosition(x, y)

                stillInGameChecker = true
            }
        }
        if(!stillInGameChecker)
            stillInGame=false
    }

    override fun shouldRemove(): Boolean {
        return this.isDead() || !stillInGame
    }

    fun isDead() : Boolean{
        //query
        var dead = true
        GameSession.otherPlayers?.forEach { el -> if(el.id == id) dead= el.dead}
        return dead
    }


    /**
     * Sets the ghost position
     * @param x : ghost's X position
     * @param y : ghost's Y position
     */
    fun setPosition(x: Float, y: Float) {
        if(x > RADIUS && x < context.getWidthScreen() - RADIUS){
            positionX = x
        } else if (x < RADIUS) {
            positionX = RADIUS;
        } else {
            positionX = context.getWidthScreen() - RADIUS
        }
        positionY = y;
    }

    override fun getCenter(): PointF {
        return PointF(this.positionX, this.positionY)
    }

    override fun getRadius(): Float {
        return RADIUS
    }

}