package com.skycombat.game.model

import android.graphics.*
import com.skycombat.game.GameView
import com.skycombat.game.model.component.HealthBar
import kotlin.math.pow

/**
 * Represents an Player
 * @param positionX : position on the x axis
 * @param positionY : position on the y axis
 * @param scene : the gameview onto which the player will be drawn
 */
class Player( positionX : Float, positionY : Float, scene : GameView)
        : AbstEntity(positionX, positionY, scene), HasHealth {

    override val MAX_HEALTH : Float = 1000f
    override var shotEveryUpdates : Int = 60
    val RADIUS : Float = 50F
    override lateinit var healthBar : HealthBar;

    init {
        paint.color = Color.GREEN
        var hbColor : Paint = Paint()
        hbColor.setColor(Color.GREEN)
        healthBar = HealthBar(
                RectF(20f, scene.getMaxHeight().toFloat() - 50, scene.getMaxWidth().toFloat()-20, scene.getMaxHeight().toFloat() - 10),
                hbColor,
                this
        );
    }

    /**
     * Draws the player and player's healthbar
     * @param canvas : the canvas onto which the player will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(positionX, positionY, RADIUS, paint)
        healthBar.draw(canvas)
    }

    /**
     * Update bullets and powerups relative to the player
     * @param bullets : the bullets the player has shot
     * @param powerUps : the powerUps appearing on the screen
     * @see Bullet
     * @see HealthBar
     */
    override fun update() {

        super.updateShots()

        /*powerUps.forEach{
            el -> run {
                val dist = sqrt((
                            ((el.getX()?: 0f) - positionX).pow(2) +
                            ((el.getY()?: 0f) - positionY).pow(2)).toDouble()
                )
                if (dist < PowerUp.RADIUS + RADIUS) {
                    el.apply(this)
                }
            }
        }

        bullets.forEach{
            el -> run {
                val dist = sqrt((
                            (el.x - positionX).pow(2) +
                                    (el.y - positionY).pow(2)).toDouble())
                if (dist < Bullet.RADIUS + RADIUS) {
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

    override fun isMerging(_particle:AbstParticle):Boolean{
        val x=_particle.getPositionX()
        val y=_particle.getPositionY()
        val r=_particle.getRadius()
        return (Math.sqrt(( (x - positionX).pow(2) + (y - positionY).pow(2) ).toDouble()) < (r + RADIUS))
    }

    override fun getWidth(): Float = 2F*RADIUS
    override fun getHeight():Float = 2F*RADIUS


    /**
     * Shoots the bullet in the right direction
     * @see Bullet
     */
    override fun shoot() : Unit{
        scene.addParticle(Bullet( positionX , positionY - RADIUS, 15f, 15f, scene, AbstParticle.Direction.UP, AbstParticle.Target.ENEMIES))
    }

    override fun getCurrentHealth(): Float {
        return health
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

}