package com.skycombat.game.model

import android.content.Context
import android.graphics.*
import android.transition.Scene
import android.util.Log
import com.skycombat.game.GameView
import com.skycombat.game.model.component.HealthBar
/**
 * Represents a bullet
 * @param x : axis x coordinate of the bullet
 * @param y : axis y coordinate of the bullet
 * @param direction : direction of the bullet
 * @param scene : the gameview onto which the bullet will be drawn
 */
class Bullet(x : Float,
             y : Float,
             radius:Float,
             speed:Float,
             scene: GameView,
             direction : AbstParticle.Direction,
             target: AbstParticle.Target,
             var damage:Float = 20F
) : AbstParticle(x,y,radius,speed,scene,direction,target){

    private var paint=Paint()
    init {
        paint.color = if(direction == AbstParticle.Direction.DOWN) Color.RED else Color.GREEN
    }
    /**
     * Draws the bullet
     * @param canvas : the canvas onto which the bullet will be drawn
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(positionX, positionY , RADIUS , paint)
    }

    override fun handleHit(ent: AbstEntity){
        setHit();
        ent.addDeltaHealth(-damage);
    }

}