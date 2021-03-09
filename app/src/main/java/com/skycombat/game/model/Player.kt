package com.skycombat.game.model

import android.graphics.*
import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.Player
import com.skycombat.R
import com.skycombat.game.GameSession
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.bullet.strategy.PlayerCollisionStrategy
import com.skycombat.game.model.component.HealthBar
import com.skycombat.game.model.component.PlayerHealthBar
import com.skycombat.game.model.event.ShootObserver
import com.skycombat.game.model.event.ShootListener
import com.skycombat.game.model.support.CanShoot
import com.skycombat.game.model.support.Circle
import com.skycombat.game.model.support.GUIElement
import java.util.*

/**
 * Represents an Player
 * @param positionX : position on the x axis
 * @param positionY : position on the y axis
 * @param radius : player's radius
 * @param scene : the gameview onto which the player will be drawn
 */
class Player() : HasHealth, Circle, GUIElement, CanShoot {

    //val paint : Paint = Paint();
    companion object{
        val MAX_HEALTH : Float = 500f
        val RADIUS: Float = 90F;
    }
    var updatesFromEndShield: Long= 0
    override var health : Float = MAX_HEALTH
    var positionX:Float
    var positionY:Float
    var playerImg : Bitmap

    var healthBar : HealthBar;
    var context: ViewContext = ViewContext.getInstance()

    override var weapon:Weapon = Weapon(this, Weapon.BulletType.CLASSIC, PlayerCollisionStrategy())
    override var shootObserver = ShootObserver()

    init {
        positionX=context.getWidthScreen()/2F
        positionY= context.getHeightScreen()/ 5 * 4
        //paint.color = Color.GREEN
        healthBar = PlayerHealthBar(this);
        playerImg= Bitmap.createScaledBitmap((BitmapFactory.decodeResource(context.getResources(), R.drawable.player)),RADIUS.toInt()*2,RADIUS.toInt()*2,false)
    }
    /**
     * Draws the player and player's healthbar
     * @param canvas : the canvas onto which the player will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(playerImg,positionX- RADIUS/2,positionY- RADIUS/2,null)
        healthBar.draw(canvas)
    }

    override fun shouldRemove(): Boolean {
        return this.isDead()
    }

    fun applyShield(duration: Long){
        updatesFromEndShield = duration
    }
    fun hasShield():Boolean{
        return updatesFromEndShield >0
    }

    override fun isDamageable():Boolean{
        return !hasShield();
    }

    override fun update() {

        weapon.update()

        if(updatesFromEndShield >0) updatesFromEndShield --

        healthBar.update()
    }


    /**
     * Sets the player position
     * @param x : player's X position
     * @param y : player's Y position
     */
    fun setPosition(x: Float, y: Float) {
        if(x > RADIUS && x < context.getWidthScreen() - RADIUS){
            positionX = x
        } else if (x < RADIUS) {
            positionX = RADIUS;
        } else {
            positionX = context.getWidthScreen() - RADIUS
        }
        positionY = y;

        updateRemotePosition(x, y)
    }


    var contMutate: Int =0
    private fun updateRemotePosition(x: Float, y: Float){
        if(contMutate >= 1000) {
            var playerOnline = Player.builder()
                .name(GameSession.player?.name)
                .id(GameSession.player?.id)
                .gameroom(GameSession.player?.gameroom)
                .positionX((x).toDouble())
                .positionY((y).toDouble())
                .score((Math.random() * 10000).toInt())
                .lastinteraction(Temporal.Timestamp.now())
                .build()
            val amp = Amplify.API.mutate(
                ModelMutation.update(playerOnline),
                { response ->
                    Log.i(
                        "MyAmplifyApp",
                        "updated position with id: " + response.data.id
                    )
                },
                { error -> Log.e("MyAmplifyApp", "update position failed", error) }
            )
            amp?.cancel()
            contMutate=0
        }
        else
            contMutate++
    }


    /**
     * Shoots the bullet in the right direction
     * @see Bullet
     */

    fun setBulletType(bulletType: Weapon.BulletType){
        weapon.setBulletType(bulletType)
    }

    override fun getMaxHealth(): Float {
        return MAX_HEALTH
    }

    override fun getCenter(): PointF {
        return PointF(this.positionX, this.positionY)
    }

    override fun getRadius(): Float {
        return RADIUS
    }

    override fun startPointOfShoot(): PointF {
        return PointF(positionX, positionY - RADIUS - 2F)
    }

}