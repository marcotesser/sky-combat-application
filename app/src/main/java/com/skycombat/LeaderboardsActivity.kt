package com.skycombat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView

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

        findViewById<TextView>(R.id.playerleaderboard).text = setLeaderboardText(1)
        findViewById<Button>(R.id.playersconfitti).isEnabled=false

        findViewById<Button>(R.id.playersconfitti).setOnClickListener{
            findViewById<TextView>(R.id.playerleaderboard).text = setLeaderboardText(1)
            findViewById<Button>(R.id.playersconfitti).isEnabled=false
            findViewById<Button>(R.id.punteggiomaggiore).isEnabled=true
        }
        findViewById<Button>(R.id.punteggiomaggiore).setOnClickListener{
            findViewById<TextView>(R.id.playerleaderboard).text = setLeaderboardText(2)
            findViewById<Button>(R.id.playersconfitti).isEnabled=true
            findViewById<Button>(R.id.punteggiomaggiore).isEnabled=false
        }
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }

    fun setLeaderboardText(code: Int) : String{
        //fare le query qua e creare una stringa contenente la posizione del player, il nome e lo score
        var leaderboardText: String
        if(code==1){
            leaderboardText="1 Vittorio 10000\n" +
                    "2 Marco 2000\n" +
                    "3 Riccardo 3000\n" +
                    "4 Samuele 1000\n" +
                    "5 Alberto 900\n" +
                    "6 Alice 50 \n" +
                    "7 Giovanni 20\n" +
                    " 1 Vittorio 10000\n" +
                    "2 Marco 2000\n" +
                    "3 Riccardo 3000\n" +
                    "4 Samuele 1000\n" +
                    "5 Alberto 900\n" +
                    "6 Alice 50 \n" +
                    "7 Giovanni 20\n"+
                    " 1 Vittorio 10000\n" +
                    "2 Marco 2000\n" +
                    "3 Riccardo 3000\n" +
                    "4 Samuele 1000\n" +
                    "5 Alberto 900\n" +
                    "6 Alice 50 \n" +
                    "7 Giovanni 20\n"+
                    " 1 Vittorio 10000\n" +
                    "2 Marco 2000\n" +
                    "3 Riccardo 3000\n" +
                    "4 Samuele 1000\n" +
                    "5 Alberto 900\n" +
                    "6 Alice 50 \n" +
                    "7 Giovanni 20\n"
        }else{
            leaderboardText="1 Marco 400\n" +
                    "2 Vittorio 300\n" +
                    "3 Riccardo 200\n" +
                    "4 Samuele 100\n" +
                    "5 Alberto 90\n" +
                    "6 Alice 40 \n" +
                    "7 Giovanni 20\n" +
                    "1 Marco 400\n" +
                    "2 Vittorio 300\n" +
                    "3 Riccardo 200\n" +
                    "4 Samuele 100\n" +
                    "5 Alberto 90\n" +
                    "6 Alice 40 \n" +
                    "7 Giovanni 20\n" +
                    "1 Marco 400\n" +
                    "2 Vittorio 300\n" +
                    "3 Riccardo 200\n" +
                    "4 Samuele 100\n" +
                    "5 Alberto 90\n" +
                    "6 Alice 40 \n" +
                    "7 Giovanni 20\n" +
                    "1 Marco 400\n" +
                    "2 Vittorio 300\n" +
                    "3 Riccardo 200\n" +
                    "4 Samuele 100\n" +
                    "5 Alberto 90\n" +
                    "6 Alice 40 \n" +
                    "7 Giovanni 20\n" +
                    "1 Marco 400\n" +
                    "2 Vittorio 300\n" +
                    "3 Riccardo 200\n" +
                    "4 Samuele 100\n" +
                    "5 Alberto 90\n" +
                    "6 Alice 40 \n" +
                    "7 Giovanni 20\n"

        }
        return leaderboardText

    }
}