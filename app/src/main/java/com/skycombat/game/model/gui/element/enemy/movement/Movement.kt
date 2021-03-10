package com.skycombat.game.model.gui.element.enemy.movement

import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.gui.element.enemy.Enemy

class Movement (private val deltaX: Int, private val deltaY: Int, private var updateFor: Int) {

    val context: ViewContext = ViewContext.getInstance()
    var left: Float = -100f
    var top: Float = -100f
    var horizontalAttitude : Int =1
    var verticalAttitude : Int= 1


    fun move(enemy: Enemy){

        if(updateFor > 0) {

            val randleft = (deltaX * (horizontalAttitude))
            val randtop = (deltaY * (verticalAttitude))
            if (left + randleft + enemy.getWidth() > enemy.context.getWidthScreen()) {
                horizontalAttitude = -1
            } else if (left +randleft < 0) {
                horizontalAttitude = 1
            }
            if (top + randtop + enemy.getHeight() > (enemy.context.getHeightScreen()) * 0.3) {
                verticalAttitude = -1
            } else if (top + randtop < 0) {
                verticalAttitude = 1
            }
            left += randleft
            top += randtop

        }else{
            left+= 5
            top += 3
        }
        updateFor--
    }
}