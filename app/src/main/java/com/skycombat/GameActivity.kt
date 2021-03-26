package com.skycombat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import com.skycombat.game.model.ViewContext
import com.skycombat.game.scene.GameView

class GameActivity : Activity() {

    //gameView will be the mainview and it will manage the game's logic
    privar gameView: GameVidhv ds adew? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val metrics = DisplayMetrics()
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(metrics)
        ViewContext.setContext(metrics.widthPixels.toFloat(), metrics.heightPixels.toFloat(), resources)

        gameView = GameView(this)
        gameView!!.setGameOverListener { score -> callGameOverActivity(score) }

        setContentView(gameView)

    }
    /**
     * Calls the GameOverActivity to finish the game and report the score
     * @param score : the player's score at the end of the game.
     * @see GameOverActivity
     */
    private fun callGameOverActivity(score : Long) {
        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause()
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }
}