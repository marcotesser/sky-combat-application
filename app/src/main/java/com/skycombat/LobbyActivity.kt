package com.skycombat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
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

        MultiplayerSession.player = null
        MultiplayerSession.opponents.clear()
        MultiplayerSession.opponents = CopyOnWriteArrayList()

        findViewById<Button>(R.id.remove_from_queue).setOnClickListener{
            this.removeFromQueue()
            this.finish()
        }

        Amplify.API.subscribe(
                ModelSubscription.onCreate(Player::class.java),
                { Log.i("ApiQuickStart", "Subscription established") },
                { onCreated ->
                    Log.e("giocatore arrivato", onCreated.toString())
                    if (onCreated.data.id == id && MultiplayerSession.player == null) {
                        MultiplayerSession.player = onCreated.data
                        MultiplayerSession.opponents = CopyOnWriteArrayList(
                            onCreated.data.gameroom.players.filter { op ->
                                op.id != onCreated.data.id
                            }
                        )
                        Log.e("gameroom id", MultiplayerSession.player!!.gameroom.id)
                        startGameIfReady()
                    } else if (
                        MultiplayerSession.player != null &&
                        onCreated.data.gameroom.id == MultiplayerSession.player!!.gameroom.id) {
                        Log.e("gameroom id giocatore aggiunto dopo", onCreated.data.gameroom.id)
                            MultiplayerSession.opponents.add(onCreated.data)
                            startGameIfReady()
                    }
                },
                { onFailure -> Log.e("ApiQuickStart", "Subscription failed", onFailure) },
                { Log.i("ApiQuickStart", "Subscription completed") }
        )
    }
     private fun startGameIfReady(){
        if(MultiplayerSession.player != null &&
            MultiplayerSession.opponents.size == MultiplayerSession.player!!.gameroom.gamers - 1){
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
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

    override fun onPause() {
        super.onPause()
        this.removeFromQueue()
        super.finish()
    }
    override fun onBackPressed() {
        this.removeFromQueue()
        super.onBackPressed()
    }
}