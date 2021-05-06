package com.skycombat

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.api.graphql.GraphQLOperation
import com.amplifyframework.api.graphql.model.ModelSubscription
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Player
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.skycombat.game.multiplayer.MultiplayerSession
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.concurrent.CopyOnWriteArrayList

class LobbyActivity : AppCompatActivity() {
    lateinit var id : String
    val session = MultiplayerSession.reset()
    val timer = object: CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            findViewById<TextView>(R.id.countDown).setText("${millisUntilFinished/1000} secondi rimanenti")
        }

        override fun onFinish() {
            onBackPressed()
        }
    }.start()

    override fun onCreate(savedInstanceState: Bundle?) {
        if(!intent.hasExtra("id-player")){
            this.finish()
        }

        id = intent.getStringExtra("id-player")!!
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )



        findViewById<ImageButton>(R.id.remove_from_queue).setOnClickListener{
            this.removeFromQueue()
            this.finish()
        }
        var sub : GraphQLOperation<Player>? = null
        sub = Amplify.API.subscribe(
                ModelSubscription.onCreate(Player::class.java),
                { Log.i("ApiQuickStart", "Subscription established") },
                { onCreated ->
                    if (onCreated.data.id == id && session.player == null) {
                        session.set(
                            onCreated.data,
                            CopyOnWriteArrayList(
                                onCreated.data.gameroom.players.filter { op ->
                                    op.id != onCreated.data.id
                                }
                            )
                        )
                        startGameIfReady(sub)
                    } else if (
                        session.player != null &&
                        onCreated.data.gameroom.id == session.player!!.gameroom.id) {
                        session.opponents.add(onCreated.data)
                        startGameIfReady(sub)
                    }
                },
                { onFailure -> Log.e("ApiQuickStart", "Subscription failed", onFailure) },
                { Log.i("ApiQuickStart", "Subscription completed") }
        )
    }
     private fun startGameIfReady(subscription : GraphQLOperation<Player>?){
        if(session.player != null &&
            session.opponents.size == session.player!!.gameroom.gamers - 1){

                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
                Thread {
                    try {
                        subscription?.cancel()
                    } catch (ex: Exception) { }
                }.start()

        }
    }

    private fun removeFromQueue(){
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val url = "https://kqkytn0s9f.execute-api.eu-central-1.amazonaws.com/V1/remove-from-pendency"

        val stringRequest: StringRequest = object : StringRequest(
                Method.POST, url,
                { resp -> Log.i("RESP", "RIMOSSO DA QUEUE: $resp") },
                { resp -> Log.wtf("RESP ERRORE", "ERRORE RIMUOVENDOMI DALLA QUEUE: $resp") }
        ) {
            override fun getBody(): ByteArray {
                return JSONObject()
                        .put("delete", id)
                        .toString()
                        .toByteArray(Charset.defaultCharset())
            }
            override fun getHeaders():Map<String, String> {
                return mapOf(
                    "Authorization" to AWSMobileClient.getInstance().tokens.idToken.tokenString
                )
            }
        }
        requestQueue.add(stringRequest)
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
        //this.removeFromQueue()
        super.finish()
    }
    override fun onBackPressed() {
        this.removeFromQueue()
        super.onBackPressed()
    }


}

