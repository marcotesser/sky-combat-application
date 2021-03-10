package com.skycombat.game.model.gui.element.enemy.movement

import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.gui.element.enemy.Enemy
import com.skycombat.game.model.factory.PowerUpFactory

class Movement (code : Int) {

    val context: ViewContext = ViewContext.getInstance()
    var left: Float = -100f
    var top: Float = -100f
    var horizontalAttitude : Int =1
    var verticalAttitude : Int= 1

    var deltaX: Int=0
    var deltaY: Int=0
    var updatesLimit: Int=0
    init{
        when(code) {
            1-> {
                deltaX=4
                deltaY=3
                updatesLimit=600
            }
            2-> {
                deltaX=3
                deltaY=4
                updatesLimit=500
            }
            3-> {
                deltaX=6
                deltaY=2
                updatesLimit=700
            }
            4->{
                deltaX=2
                deltaY=5
                updatesLimit=400
            }
        }
    }

    open fun move(enemy: Enemy){

        if(updatesLimit>0) {

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
        updatesLimit--
    }
}