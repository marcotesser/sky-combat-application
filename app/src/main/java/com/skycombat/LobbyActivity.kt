package com.skycombat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.api.ApiOperation
import com.amplifyframework.api.graphql.model.ModelSubscription
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Player
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.skycombat.game.multiplayer.MultiplayerSession
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.HashMap
import java.util.concurrent.CopyOnWriteArrayList

class LobbyActivity : AppCompatActivity() {
    var id : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        id = intent.getStringExtra("id-player");
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        MultiplayerSession.player = null
        MultiplayerSession.opponents.clear()

        findViewById<Button>(R.id.remove_from_queue).setOnClickListener{
            this.removeFromQueue()
            this.finish()
        }
        if(this.id != null) {
            val sub = Amplify.API.subscribe(
                    ModelSubscription.onCreate(Player::class.java),
                    { Log.i("ApiQuickStart", "Subscription established") },
                    { onCreated ->
                        if (onCreated.data.id == this.id) {
                            MultiplayerSession.player = onCreated.data;
                            MultiplayerSession.opponents = CopyOnWriteArrayList(onCreated.data.gameroom.players.filter {
                                op -> op.id != onCreated.data.id
                            })
                            startGameIfReady()
                        } else {
                            if (MultiplayerSession.player != null && onCreated.data.gameroom.id == MultiplayerSession.player!!.gameroom.id) {
                                MultiplayerSession.opponents.add(onCreated.data)
                                startGameIfReady()
                            }
                        }
                    },
                    { onFailure -> Log.e("ApiQuickStart", "Subscription failed", onFailure) },
                    { Log.i("ApiQuickStart", "Subscription completed") }
            )
        }
    }
     private fun startGameIfReady(){
        if(MultiplayerSession.player != null && MultiplayerSession.opponents.size == MultiplayerSession.player!!.gameroom.gamers - 1){
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun removeFromQueue(){
        if(id != null) {
            val requestQueue: RequestQueue = Volley.newRequestQueue(this);
            val url = "https://kqkytn0s9f.execute-api.eu-central-1.amazonaws.com/V1/remove-from-pendency";
            val jsonBody = JSONObject();
            jsonBody.put("delete", id);
            val requestBody: String = jsonBody.toString();

            val stringRequest: StringRequest = object : StringRequest(
                    Method.POST,
                    url,
                    { resp -> Log.i("RESP", "RIMOSSO DA QUEUE: $resp") },
                    { resp -> Log.wtf("RESP ERRORE", "ERRORE RIMUOVENDOMI DALLA QUEUE: $resp") }
            ) {
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
                override fun getHeaders():Map<String, String> {
                    val params = HashMap<String, String>()
                    params["Authorization"] = AWSMobileClient.getInstance().tokens.idToken.tokenString
                    return params
                }
            }
            requestQueue.add(stringRequest)
        }
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