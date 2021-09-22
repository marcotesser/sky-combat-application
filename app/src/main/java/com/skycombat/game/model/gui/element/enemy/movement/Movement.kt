package com.skycombat.game.model.gui.element.enemy.movement

import com.skycombat.game.model.gui.element.enemy.Enemy

class Movement (private val deltaX: Int, private val deltaY: Int, private var updateFor: Int) {

    var horizontalAttitude : Int =1
    var verticalAttitude : Int= 1


    fun move(enemy: Enemy){

        if(updateFor > 0) {

            val left = (deltaX * (horizontalAttitude))
            val top = (deltaY * (verticalAttitude))
            if (enemy.left + left + enemy.getWidth() > enemy.displayDimension.width) {
                horizontalAttitude = -1
            } else if (enemy.left +left < 0) {
                horizontalAttitude = 1
            }
            if (enemy.top + top + enemy.getHeight() > (enemy.displayDimension.height) * 0.3) {
                verticalAttitude = -1
            } else if (enemy.top + top < 0) {
                verticalAttitude = 1
            }
            enemy.left += left
            enemy.top += top

        }else{
            enemy.left+= 5
            enemy.top += 3
        }
        updateFor--
    }
}