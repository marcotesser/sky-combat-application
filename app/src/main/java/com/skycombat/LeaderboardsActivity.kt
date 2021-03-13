package com.skycombat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.amplifyframework.core.Amplify
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class LeaderboardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_leaderboards)

        findViewById<Button>(R.id.playersconfitti).isEnabled=false
        findViewById<TextView>(R.id.playerleaderboard).text = "Loading ..."
        getLeaderboard ("defeated")

        findViewById<Button>(R.id.playersconfitti).setOnClickListener{
            findViewById<Button>(R.id.playersconfitti).isEnabled=false
            findViewById<Button>(R.id.punteggiomaggiore).isEnabled=true
            findViewById<TextView>(R.id.playerleaderboard).text = "Loading ..."
            getLeaderboard ("defeated")
        }

        findViewById<Button>(R.id.punteggiomaggiore).setOnClickListener{
            findViewById<Button>(R.id.playersconfitti).isEnabled=true
            findViewById<Button>(R.id.punteggiomaggiore).isEnabled=false
            findViewById<TextView>(R.id.playerleaderboard).text = "Loading ..."
            getLeaderboard ("score")
        }
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }

    private fun getLeaderboard (scope: String) {
        val url : String = if(Amplify.Auth.currentUser != null) {
           "https://dmh7jq3nqi.execute-api.eu-central-1.amazonaws.com/V1/get-leaderboard?scope=$scope&username=${Amplify.Auth.currentUser.username}"
        }
        else {
            "https://dmh7jq3nqi.execute-api.eu-central-1.amazonaws.com/V1/get-leaderboard?scope=$scope"
        }
        val queue  = Volley.newRequestQueue(this)
        val jsonObjectRequest = object: JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                if(response.has("me")) {
                    val me: JSONObject = response.getJSONObject("me")
                    val myScore : String = "Sei in posizione: ${me.getString("pos")} \nIl tuo punteggio è: ${me.getString(scope)}"
                    runOnUiThread {
                        findViewById<TextView>(R.id.playerScore).text = myScore
                    }
                }

                val leaderboard: JSONArray = response.getJSONArray("leaderboard")
                var row : String = ""
                for (index in 0 until leaderboard.length()) {
                    row += "${index+1} ${leaderboard.getJSONObject(index).getString("username")} ${leaderboard.getJSONObject(index).getString(scope)} \n"
                }

                runOnUiThread {
                    findViewById<TextView>(R.id.playerleaderboard).text = row
                }
            },
            { error ->
                Log.e("errore", error.toString())
                runOnUiThread {
                    findViewById<TextView>(R.id.playerleaderboard).text = "Errore richiesta classifica"
                }
            }
        ) {}
        queue.add(jsonObjectRequest)
    }

}