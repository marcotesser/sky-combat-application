package com.skycombat.game.model.gui.element.ghost

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.model.geometry.Circle
import com.skycombat.game.model.geometry.Entity
import com.skycombat.game.model.geometry.Rectangle
import com.skycombat.game.model.gui.element.GUIElement
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.ghost.strategy.AimedPositionStrategy
import com.skycombat.game.model.gui.properties.AimToPositionX
import com.skycombat.game.scene.ViewContext

class Ghost(val aimedPos : AimedPositionStrategy, val velocity: Float) : GUIElement,  Circle, AimToPositionX {
    companion object{
        var RADIUS: Float = Player.RADIUS
    }
    var context: ViewContext = ViewContext.getInstance()
    private var x = context.getWidthScreen() / 2F
    var aimedPositionX: Float = x
    var y = context.getHeightScreen() / 5 * 4
    private var dead: Boolean = false
    var deadAt : Long? = null
    var paint = Paint()
    var ghostImg : Bitmap
    init {
        paint.alpha = 127
        ghostImg = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(context.getResources(), R.drawable.opponent),
            RADIUS.toInt()*2,
            RADIUS.toInt()*2,
            false
        )
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

    override fun draw(canvas: Canvas?) {
        if(isAlive()) {
            canvas?.drawBitmap(ghostImg, x - RADIUS / 2, y - RADIUS / 2, paint)
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