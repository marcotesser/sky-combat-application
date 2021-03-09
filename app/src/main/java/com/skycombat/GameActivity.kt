package com.skycombat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import com.skycombat.game.gameview.GameView
import com.skycombat.game.GameOverListener
import com.skycombat.game.gameview.GameViewMultiplayer
import com.skycombat.game.gameview.GameViewSingleplayer

class GameActivity(var gameType:GameType) : Activity() {
    enum class GameType{
        SINGLEPLAYER, MULTIPLAYER
    }

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

        when(gameType){
            GameType.MULTIPLAYER -> gameView = GameViewMultiplayer(this, METRICS.widthPixels.toFloat(), METRICS.heightPixels.toFloat())
            GameType.SINGLEPLAYER -> gameView = GameViewSingleplayer(this, METRICS.widthPixels.toFloat(), METRICS.heightPixels.toFloat())
        }
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
        this.finish()
        super.onBackPressed()
    }

}