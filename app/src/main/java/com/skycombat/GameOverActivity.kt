package com.skycombat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.core.Amplify
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.skycombat.game.scene.GameView
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.HashMap

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_game_over)

        findViewById<Button>(R.id.backToHome).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val gameType : GameActivity.GAMETYPE =
                intent.getSerializableExtra(GameActivity.SIGLA_TYPE)!! as GameActivity.GAMETYPE
        val score = intent.getLongExtra(GameActivity.SIGLA_SCORE, 0)

        findViewById<Button>(R.id.playAgain).setOnClickListener {
            if(gameType==GameActivity.GAMETYPE.SINGLE_PLAYER) {
                startActivity(Intent(this, GameActivity::class.java))
            }
            else {
                startActivity(Intent(this, LobbyActivity::class.java))
            }
        }

        findViewById<TextView>(R.id.score).text = score.toString()
        if(Amplify.Auth.currentUser != null) {
            upLoad(gameType, score)
        }
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }

    private fun upLoad(gameType : GameActivity.GAMETYPE, score : Long) {
        Log.e("cosa invia?",gameType.sigla() + score.toString())
        val requestQueue: RequestQueue = Volley.newRequestQueue(this);
        val jsonBody = JSONObject();
        val url : String
        if(gameType==GameActivity.GAMETYPE.SINGLE_PLAYER) {
            url = "https://dmh7jq3nqi.execute-api.eu-central-1.amazonaws.com/V1/update-singleplayer-score"
            jsonBody.put("score", score);
        }
        else {
            url = "https://dmh7jq3nqi.execute-api.eu-central-1.amazonaws.com/V1/update-multiplayer-score"
            jsonBody.put("defeated", score);
        }

        val requestBody: String = jsonBody.toString();

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            url,
            { resp -> Log.i("RESP", "Inviato il risultato: $resp") },
            { resp -> Log.wtf("RESP ERRORE", "Errore invio: $resp") }
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