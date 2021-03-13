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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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

        val gameType = intent.getStringExtra(GameActivity.SIGLA_TYPE)!!
        val score = intent.getIntExtra(GameActivity.SIGLA_SCORE, 0)

        findViewById<Button>(R.id.playAgain).setOnClickListener {
            if(gameType=="single-player") {
                startActivity(Intent(this, GameActivity::class.java))
            }
            else {
                startActivity(Intent(this, LobbyActivity::class.java))
            }
        }

        findViewById<TextView>(R.id.score).text = score.toString()
        if(Amplify.Auth.currentUser != null) {
            upLoad(gameType, score.toInt())
        }
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }

    private fun upLoad(gameType : String, score : Int) {
        var url : String = ""
        if(gameType=="single-player") {
            url = "https://dmh7jq3nqi.execute-api.eu-central-1.amazonaws.com/V1/update-singleplayer-score?score=$score"
        }
        else if(gameType=="multi-player") {
            url = "https://dmh7jq3nqi.execute-api.eu-central-1.amazonaws.com/V1/update-multiplayer-score?score=$score"
        }
        Log.e("url",url)
        val queue  = Volley.newRequestQueue(this)
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            { },
            { error ->
                Log.e("errore", error.toString())
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = AWSMobileClient.getInstance().tokens.idToken.tokenString
                return params
            }
        }
        queue.add(jsonObjectRequest)
    }
}