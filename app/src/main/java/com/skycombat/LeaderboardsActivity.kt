package com.skycombat

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.skycombat.api.leaderboard.Leaderboard
import com.skycombat.api.leaderboard.Me

class LeaderboardsActivity : AppCompatActivity() {
    companion object{
        enum class SCOPE{
            SCORE {
                override fun toString(): String {
                    return "score"
                }
            }, DEFEATED{
                override fun toString(): String {
                    return "defeated"
                }
            };
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_leaderboards)

        val playerSconfitti = findViewById<ImageButton>(R.id.playersconfitti)
        val punteggioMaggiore = findViewById<ImageButton>(R.id.punteggiomaggiore)
        val playerLeaderboard = findViewById<TextView>(R.id.playerleaderboard)
        val whileLoading = "Loading ..."
        playerSconfitti.isEnabled=false
        playerLeaderboard.text = whileLoading
        getLeaderboard (SCOPE.DEFEATED)

        findViewById<ImageButton>(R.id.playersconfitti).setOnClickListener{
            playerSconfitti.isEnabled=false
            punteggioMaggiore.isEnabled=true
            playerLeaderboard.text = whileLoading
            getLeaderboard(SCOPE.DEFEATED)
        }

        findViewById<ImageButton>(R.id.punteggiomaggiore).setOnClickListener{
            playerSconfitti.isEnabled=true
            punteggioMaggiore.isEnabled=false
            playerLeaderboard.text = whileLoading
            getLeaderboard(SCOPE.SCORE)
        }
    }

    private fun getLeaderboard (scope: SCOPE) {
        val url : String = if(Amplify.Auth.currentUser != null) {
           "https://dmh7jq3nqi.execute-api.eu-central-1.amazonaws.com/V1/get-leaderboard?scope=$scope&username=${Amplify.Auth.currentUser.username}"
        }
        else {
            "https://dmh7jq3nqi.execute-api.eu-central-1.amazonaws.com/V1/get-leaderboard?scope=$scope"
        }
        val queue  = Volley.newRequestQueue(this)
        val jsonObjectRequest = object: JsonObjectRequest(Method.GET, url, null,
            { response ->
                val leaderboard = Leaderboard(response)
                val me = leaderboard.me
                if(me != null) {
                    setPlayerPositionGUI(scope, me)
                }
                setLeaderboardGUI(leaderboard, scope)
            },
            { error -> Log.e("ERRORE LEADERBOARD", error.toString())}
        ) {}
        queue.add(jsonObjectRequest)
    }

    private fun setPlayerPositionGUI(scope: SCOPE, me: Me) {
        runOnUiThread {
            findViewById<TextView>(R.id.playerScore).text = when (scope) {
                SCOPE.DEFEATED -> "Sei in posizione: ${me.pos.defeated} \nIl tuo punteggio è: ${me.defeated}"
                SCOPE.SCORE -> "Sei in posizione: ${me.pos.score} \nIl tuo punteggio è: ${me.score}"
            }
        }
    }

    private fun setLeaderboardGUI(leaderboard: Leaderboard, scope: SCOPE) {
        runOnUiThread {
            findViewById<TextView>(R.id.playerleaderboard).text = leaderboard.results
                .map { result ->
                    result.username + " " + when (scope) {
                        SCOPE.DEFEATED -> result.defeated.toString()
                        SCOPE.SCORE -> result.score.toString()
                    } + "\n"
                }.mapIndexed{ index, el ->
                    " ${index+1} $el"
                }.joinToString(separator = "")
        }
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }

}