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
import com.google.android.material.math.MathUtils
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
    var x : Float = 0F
    var x2 : Float = 0F
    var y : Float = 0F
    var y2 : Float = 0F
    var lastInteraction : Temporal.Timestamp = Temporal.Timestamp()
    var latencyUpdates : Int = 0

    var stillInGame : Boolean = true

    init {
        paint.color = Color.WHITE
        var hbColor : Paint = Paint()
        hbColor.setColor(Color.WHITE)

//        var playerGhosted = Player.builder()
//            .name(name)
//            .id(id)
//            .build()

//        subscriptionPositioning()
    }

//    fun subscriptionPositioning() {
//        val amp = Amplify.API.subscribe(
//            ModelSubscription.onUpdate(Player::class.java),
//            { Log.i("ApiQuickStart", "Subscription established") },
//            { updated ->
//                Log.i("idk", updated.data.toString())
//                if(updated.data.id.equals(id)){
//                    setPosition((updated.data.positionX).toFloat(), (updated.data.positionX).toFloat())
//                }
//            },
//            { onFailure -> Log.e("ApiQuickStart", "Subscription failed", onFailure) },
//            { Log.i("ApiQuickStart", "Subscription completed") }
//        )
//        amp?.cancel()
//    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(positionX, positionY, radius, paint)
    }

    override fun update() {
        var stillInGameChecker: Boolean = false
        GameSession.otherPlayers?.forEach { el ->
            if(el.id == id) {
                if (lastInteraction == el.lastinteraction) {
                    if (latencyUpdates > 200) {
                        val xInterpolated = MathUtils.lerp(
                            x2,
                            x,
                            0.3f
                        )
                        x2 = x
                        x = xInterpolated

                        latencyUpdates = 0
                    } else
                        latencyUpdates++
                } else {
                    lastInteraction = el.lastinteraction
                    x2 = x
                    x = (el.positionX).toFloat()

                    //nel caso si volesse fare l`interpolazione bidimensionale
                    y2 = y
                    y = (el.positionY).toFloat()

                    setPosition(x, y)

                }
                stillInGameChecker = true
            }
        }
        if(!stillInGameChecker)
            stillInGame=false
    }

    override fun shouldRemove(): Boolean {
        return this.isDead()
    }
    fun isDead() : Boolean{
        //query
        GameSession.otherPlayers?.forEach { el -> if(el.id == id) return el.dead || !stillInGame}
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