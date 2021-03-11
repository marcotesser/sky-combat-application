package com.skycombat.game.model.gui.component

import android.graphics.Bitmap
import android.graphics.Canvas
import com.skycombat.game.scene.ViewContext

class Background(private val backgroundImage: Bitmap, private val overlayImage: Bitmap) : GUIComponent{
    val viewContext = ViewContext.getInstance()

    var backY : Float= 0f
    var backY2 : Float= 0f

    override fun draw(canvas: Canvas?) {
        this.update()
        canvas?.drawBitmap(backgroundImage, 0f, backY ,null)
        canvas?.drawBitmap(backgroundImage, 0f, backY - viewContext.width.toInt()*3 ,null)
        canvas?.drawBitmap(overlayImage, 0f, backY2 ,null)
        canvas?.drawBitmap(overlayImage, 0f, (backY2 - viewContext.width.toInt()*3) ,null)

    }
    private fun update(){
        //movimento sfondo
        if(backY<viewContext.width.toInt()*3) {
            backY += 5
        }else{
            backY=0f
        }
        if(backY2<viewContext.width.toInt()*3) {
            backY2 += 7
        }else{
            backY2=0f
        }
    }

}