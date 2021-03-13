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
        val url = "https://dmh7jq3nqi.execute-api.eu-central-1.amazonaws.com/V1/get-leaderboard?scope=$scope"
        val queue  = Volley.newRequestQueue(this)
        val jsonArrayRequest = object: JsonArrayRequest(Request.Method.GET, url, null,
            { response ->

                var row : String = ""
                var myScore : String = ""

                for (index in 0..(response.length()-1)) {
                    if(Amplify.Auth.currentUser != null) {
                        Log.e("profile", Amplify.Auth.currentUser.username+" "+response.getJSONObject(index).getString("username"))
                        if(response.getJSONObject(index).getString("username")==Amplify.Auth.currentUser.username) {
                            myScore = "${index+1} ${response.getJSONObject(index).getString("username")} ${response.getJSONObject(index).getString(scope)} \n"
                            Log.e("myScore", myScore)
                        }
                    }
                    else {
                        Log.e("profile", "Incognito")
                    }
                    row += "${index+1} ${response.getJSONObject(index).getString("username")} ${response.getJSONObject(index).getString(scope)} \n"

                }
                Log.e("myScore", myScore)
                runOnUiThread {
                    findViewById<TextView>(R.id.playerleaderboard).text = row
                    findViewById<TextView>(R.id.playerScore).text = myScore
                }
            },
            { error ->
                Log.e("errore", error.toString())
                findViewById<TextView>(R.id.playerleaderboard).text = "Errore richiesta classifica"
            }
        ) {}
        queue.add(jsonArrayRequest)
    }

}