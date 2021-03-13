package com.skycombat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import android.widget.Toast
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.core.Amplify
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.JsonArray
import java.util.HashMap

import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin

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

        //findViewById<TextView>(R.id.playerleaderboard).text = setLeaderboardText(1)
        findViewById<Button>(R.id.playersconfitti).isEnabled=false
        getLeaderboard ("defeated")

        findViewById<Button>(R.id.playersconfitti).setOnClickListener{
            findViewById<Button>(R.id.playersconfitti).isEnabled=false
            findViewById<Button>(R.id.punteggiomaggiore).isEnabled=true
            getLeaderboard ("defeated")
        }

        findViewById<Button>(R.id.punteggiomaggiore).setOnClickListener{
            findViewById<Button>(R.id.playersconfitti).isEnabled=true
            findViewById<Button>(R.id.punteggiomaggiore).isEnabled=false
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
                //non esiste inline???
                //var myProfile : Boolean = (Amplify.Auth.currentUser == null) ? true : false
                var myProfile : Boolean = true
                if(Amplify.Auth.currentUser != null) {
                    myProfile = false
                }

                for (index in 0..(response.length()-1)) {
                    if (index < 10) {
                        if(myProfile==false && response.getJSONObject(index).getString("username")==Amplify.Auth.currentUser.username) {
                            myProfile = true
                        }
                        row += "${index+1} ${response.getJSONObject(index).getString("username")} ${response.getJSONObject(index).getString(scope)} \n"

                    }
                    else if(myProfile==true) {
                        break
                    }
                    else if (response.getJSONObject(index).getString("username") == Amplify.Auth.currentUser.username) {
                        myProfile = true
                        row += "$index ${response.getJSONObject(index).getString("username")} ${response.getJSONObject(index).getString(scope)} \n"
                    }
                }
                Log.e("row", row)
                runOnUiThread {
                    findViewById<TextView>(R.id.playerleaderboard).text = row
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