package com.skycombat.game.model

import android.graphics.*
import com.skycombat.game.GameView
import com.skycombat.game.model.component.HealthBar
import kotlin.math.pow
/**
 * Represents an Enemy
 * @param player : used as a base to draw the enemy
 * @param left : movement towards left
 * @param top : movement towards the top
 * @param width : enemy's width
 * @param height : enemy's height
 * @param scene : the gameview onto which the enemy will be drawn
 */
class Enemy(positionX : Float,
            positionY : Float,
            private val WIDTH: Float,
            private val HEIGHT: Float ,
            val SPEED: Float,
            scene : GameView
): AbstEntity(positionX,positionY,scene), HasHealth {

    override val MAX_HEALTH : Float = 300f
    override var shotEveryUpdates : Int = 100;
    override lateinit var healthBar : HealthBar;

    init {
        paint.color = Color.RED
        var hbColor : Paint = Paint()
        hbColor.setColor(Color.RED)
        healthBar = HealthBar(
                RectF(20f, 10f, scene.getMaxWidth().toFloat()-20, 50f),
                hbColor,
                this
        );
    }

    /**
     * Draws the player and enemy's healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(RectF(getLeft(), getTop(), getLeft()+getWidth(), getTop()+getHeight()), paint)
        healthBar.draw(canvas)
    }


    /**
     * Update bullets to the enemy
     * @param bullets : the bullets the enemy has shot
     * @see Bullet
     * @see HealthBar
     */
    override fun update() {

        super.updateShots()

        val positionFound = searchPosition()
        setPosition(positionFound.x,positionFound.y)

        /*bullets.forEach{
            el -> run {
                if (el.y+ Bullet.RADIUS < top+height && el.y- Bullet.RADIUS > top && el.x > left && el.x < left + width ) {
                    el.hit()
                    if(el.damage.toFloat() > this.health){
                        this.health = 0f;
                    }else {
                        this.health -= el.damage.toFloat()
                    }
                }
            }
        }*/

        healthBar.update()
    }

    private fun searchPosition():PointF{

        val xPlayer=scene.getPlayer().positionX
        var pos = PointF(positionX,positionY)

        if(Math.abs(positionX - xPlayer) < SPEED ){
            pos.x = xPlayer
        } else if (positionX < xPlayer){
            pos.x+= SPEED
        } else {
            pos.x -= SPEED
        }
        return pos
    }

    /**
     * Shoots the bullet in the right direction
     * @see Bullet
     */
    override fun shoot() : Unit{
        scene.addParticle(Bullet( positionX, getTop() + getHeight(), 15f, 20f, scene, AbstParticle.Direction.DOWN, AbstParticle.Target.PLAYER))
    }

    override fun isMerging(_particle:AbstParticle):Boolean{
        val x=_particle.getPositionX()
        val y=_particle.getPositionY()
        val r=_particle.getRadius()
        return ((Math.abs(x-positionX)< r+getWidth()/2F)&&(Math.abs(y-positionY)< r+getHeight()/2F))
    }

    /**
     * Checks if the enemy is dead
     * @see HealthBar
     */
    fun isDead() : Boolean{
        return this.health <= 0
    }

    private fun getLeft():Float = positionX - getWidth()/2;
    private fun getTop():Float = positionY - getHeight()/2;

    override fun getWidth(): Float = WIDTH
    override fun getHeight(): Float = HEIGHT

    override fun getCurrentHealth(): Float {
        return health
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

}