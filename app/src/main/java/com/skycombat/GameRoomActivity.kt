package com.skycombat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import com.skycombat.game.GameOverListener

import com.skycombat.game.GameRoomView
import com.skycombat.game.GameView

class GameRoomActivity : Activity() {
    private var gameRoomView: GameRoomView? = null

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


        gameRoomView = GameRoomView(this, METRICS.widthPixels.toFloat(), METRICS.heightPixels.toFloat())
        gameRoomView!!.setGameOverListener (object: GameOverListener() {
            override fun gameOver(score : Long) {
                callGameOverActivity(score)
            }
        })

        setContentView(gameRoomView)

    }
    /**
     * Calls the GameOverActivity to finish the game and report the score
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
        gameRoomView?.pause();
    }

    override fun onBackPressed() {
        // super.onBackPressed()
    }



}