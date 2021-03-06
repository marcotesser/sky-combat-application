package com.skycombat

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.skycombat.game.GameView
import com.skycombat.game.GameOverListener

class GameActivity : Activity() {

    //gameView will be the mainview and it will manage the game's logic
    private var gameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val METRICS = DisplayMetrics()
        val WINDOW_MANAGER = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        WINDOW_MANAGER.defaultDisplay.getMetrics(METRICS)


        gameView = GameView(this, METRICS.widthPixels.toFloat(), METRICS.heightPixels.toFloat())
        gameView!!.setGameOverListener (object: GameOverListener() {
            override fun gameOver(score : Long) {
                callGameOverActivity(score )
            }
        })

        setContentView(gameView)

    }
    /**
     * Calls the GameOverActivity to finish the game and report the score
     * @param score : the player's score at the end of the game.
     * @see GameOverActivity
     */
    private fun callGameOverActivity(score : Long) {
        val INTENT : Intent = Intent(this, GameOverActivity::class.java);
        INTENT.putExtra("score", score)
        startActivity(INTENT)
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause();
    }

    override fun onBackPressed() {
        // super.onBackPressed()
    }

}