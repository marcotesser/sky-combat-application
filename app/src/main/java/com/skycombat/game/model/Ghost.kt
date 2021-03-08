package com.skycombat.game.model

import android.content.Intent
import android.graphics.*
import android.util.Log
import android.widget.TextView
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.api.graphql.model.ModelSubscription
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.Player
import com.skycombat.GameRoomActivity
import com.skycombat.game.GameView
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.component.HealthBar
import com.skycombat.game.model.support.Circle
import com.skycombat.game.model.support.GUIElement
import java.util.*
import kotlin.math.pow

class Ghost(var positionX : Float, var positionY : Float, private var radius : Float, var id: String, var name: String, var scene : GameView) :
    Circle, GUIElement {

    val paint : Paint = Paint();

    init {
        paint.color = Color.WHITE
        var hbColor : Paint = Paint()
        hbColor.setColor(Color.WHITE)

//        var playerGhosted = Player.builder()
//            .name(name)
//            .id(id)
//            .build()
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Amplify.API.subscribe(
                    ModelSubscription.onUpdate(Player::class.java),
                    { Log.i("ApiQuickStart", "Subscription established") },
                    { updated ->
                     Log.i("idk", updated.data.toString())
                     if(updated.data.id.equals(id)){
                            setPosition((updated.data.positionX).toFloat(), (updated.data.positionX).toFloat())
                        }
                 },
                    { onFailure -> Log.e("ApiQuickStart", "Subscription failed", onFailure) },
                    { Log.i("ApiQuickStart", "Subscription completed") }
                )
            }
        }, 1000, 1000)
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(positionX, positionY, radius, paint)
    }

    override fun update() {
//        Amplify.API.query(
//            ModelQuery.get(
//                Player::class.java, id.toString()
//            ),
//            { response ->
//                setPosition((response.data.positionX).toFloat(), (response.data.positionX).toFloat())
//
//                Log.i("idk", response.toString())
//            },
//            { error -> Log.e("MyAmplifyApp", "Positioning ghost failure", error) }
//        )
    }

    override fun shouldRemove(): Boolean {
        return this.isDead()
    }
    fun isDead() : Boolean{
        //query
        GameSession.otherPlayers?.forEach { el -> if(el.id == id) return el.dead }
        return true
    }


    /**
     * Sets the ghost position
     * @param x : ghost's X position
     * @param y : ghost's Y position
     */
    fun setPosition(x: Float, y: Float) {
        if(x > radius && x < scene.getMaxWidth() - radius){
            positionX = x
        } else if (x < radius) {
            positionX = radius;
        } else {
            positionX = scene.getMaxWidth() - radius
        }
        positionY = y;
    }

    override fun getCenter(): PointF {
        return PointF(this.positionX, this.positionY)
    }

    override fun getRadius(): Float {
        TODO("Only for checkers")
        return radius
    }

}