package com.skycombat.game.model.gui.element.enemy.movement

import com.skycombat.game.model.gui.element.enemy.Enemy

class Movement (private val deltaX: Int, private val deltaY: Int, private var updateFor: Int) {

    var horizontalAttitude : Int =1
    var verticalAttitude : Int= 1


    fun move(enemy: Enemy){

        if(updateFor > 0) {

            val randleft = (deltaX * (horizontalAttitude))
            val randtop = (deltaY * (verticalAttitude))
            if (enemy.left + randleft + enemy.getWidth() > enemy.displayDimension.width) {
                horizontalAttitude = -1
            } else if (enemy.left +randleft < 0) {
                horizontalAttitude = 1
            }
            if (enemy.top + randtop + enemy.getHeight() > (enemy.displayDimension.height) * 0.3) {
                verticalAttitude = -1
            } else if (enemy.top + randtop < 0) {
                verticalAttitude = 1
            }
            enemy.left += randleft
            enemy.top += randtop

        }else{
            enemy.left+= 5
            enemy.top += 3
        }
        updateFor--
    }
}