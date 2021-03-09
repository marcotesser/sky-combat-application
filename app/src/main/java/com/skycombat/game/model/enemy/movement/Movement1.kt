package com.skycombat.game.model.enemy.movement

import com.skycombat.game.model.enemy.Enemy

class Movement1 : Movement {
    override fun move(enemy: Enemy) {
        val randleft = enemy.left + (Math.random() * (enemy.horizontalAttitude * 10)).toFloat()
        val randtop = enemy.top + (Math.random() * (enemy.verticalAttitude * 10)).toFloat()
        if (randleft + enemy.getWidth() > enemy.context.getWidthScreen()) {
            enemy.horizontalAttitude = -1
        } else if (randleft < 0) {
            enemy.horizontalAttitude = 1
        }
        if (randtop + enemy.getHeight() > enemy.context.getHeightScreen() * 0.3) {
            enemy.verticalAttitude = -1
        } else if (randtop < 0) {
            enemy.verticalAttitude = 1
        }
        enemy.left = randleft
        enemy.top = randtop

    }

}