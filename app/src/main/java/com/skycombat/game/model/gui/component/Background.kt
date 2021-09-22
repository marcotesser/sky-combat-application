package com.skycombat.game.model.gui.component

import android.graphics.Canvas
import com.skycombat.R
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.DrawVisitor

class Background(val displayDimension : DisplayDimension, val backgroundImage: Int = R.drawable.islands, val overlayImage: Int = R.drawable.clouds) : GUIComponent{

    var backY : Float= 0f
    var backY2 : Float= 0f

    override fun draw(canvas: Canvas?, visitor: DrawVisitor) {
        this.update()
        visitor.draw(canvas, this)

    }
    private fun update(){
        //movimento sfondo
        if(backY<displayDimension.width.toInt()*3) {
            backY += 5
        }else{
            backY=0f
        }
        if(backY2<displayDimension.width.toInt()*3) {
            backY2 += 7
        }else{
            backY2=0f
        }
    }
}