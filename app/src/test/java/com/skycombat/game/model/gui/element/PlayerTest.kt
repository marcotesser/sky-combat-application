package com.skycombat.game.model.gui.element

import android.graphics.PointF
import android.view.animation.LinearInterpolator
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.ghost.movement.LinearAimedPositionMovement
import org.junit.Assert.*
import org.junit.Test

class PlayerTest {

    @Test
    fun `should remove if player is dead`(){
        var player = Player(10f, LinearAimedPositionMovement(), DisplayDimension(10f, 10f))
        player.health = 0f

        assertTrue(player.shouldRemove())
    }

    @Test
    fun `center is valid`(){
        val width = 100f; val height = 100f
        var player = Player(0f, LinearAimedPositionMovement(), DisplayDimension(width, height))

        val tx : Float  = width/2F
        val ty : Float = height/ 5 * 4

        val x = player.getX()
        val y = player.getY()

        assertTrue(tx==x && ty==x)
    }
}