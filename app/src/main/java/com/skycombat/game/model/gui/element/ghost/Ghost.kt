package com.skycombat.game.model.gui.element.ghost

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.model.geometry.Circle
import com.skycombat.game.model.geometry.Entity
import com.skycombat.game.model.geometry.Rectangle
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.DrawVisitor
import com.skycombat.game.model.gui.element.GUIElement
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.ghost.strategy.AimedPositionStrategy
import com.skycombat.game.model.gui.properties.AimToPositionX

class Ghost(val aimedPos : AimedPositionStrategy, val velocity: Float, val displayDimension : DisplayDimension) : GUIElement,  Circle, AimToPositionX {
    companion object{
        var RADIUS: Float = Player.RADIUS
    }
    private var x = displayDimension.width / 2F
    var aimedPositionX: Float = x
    var y = displayDimension.height / 5 * 4
    private var dead: Boolean = false
    var deadAt : Long? = null
    var paint = Paint()
    var ghostImg : Int = R.drawable.opponent
    init {
        paint.alpha = 127
    }
    override fun getCenter(): PointF {
        return PointF(this.x, this.y)
    }

    override fun getRadius(): Float {
        return Player.RADIUS
    }

    override fun shouldRemove(): Boolean {
        return this.isDead()
    }

    fun kill() {
        this.dead = true
    }

    fun isDead(): Boolean {
        if(dead && deadAt == null){
            deadAt = System.currentTimeMillis()
        }
        return dead
    }

    fun isAlive(): Boolean {
        return !isDead()
    }

    override fun draw(canvas: Canvas?, visitor: DrawVisitor) {

        if(this.isAlive()) {
            visitor.draw(canvas, this)
        }
    }

    override fun update() {
        if(isAlive()){
            aimedPos.move(this)
        }
    }

    override fun setX(pos: Float) {
        this.x = pos
    }

    override fun getX(): Float {
        return this.x
    }

    override fun aimToPos(): Float {
        return this.aimedPositionX
    }

    override fun velocity(): Float {
        return velocity
    }

    override fun collide(el: Rectangle): Boolean {
        return false
    }

    override fun collide(el: Circle): Boolean {
        return false
    }

    override fun collide(el: Entity): Boolean {
        return false
    }
}