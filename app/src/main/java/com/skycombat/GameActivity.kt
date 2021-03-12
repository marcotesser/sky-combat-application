package com.skycombat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.skycombat.game.model.gui.element.ghost.Ghost
import com.skycombat.game.model.gui.element.ghost.strategy.LinearPositionStrategy
import com.skycombat.game.multiplayer.OpponentUpdaterService
import com.skycombat.game.multiplayer.MultiplayerSession
import com.skycombat.game.multiplayer.PlayerUpdaterService
import com.skycombat.game.scene.ViewContext
import com.skycombat.game.scene.GameView
import java.util.stream.Collectors
import java.util.stream.IntStream

class GameActivity : Activity() {

    //gameView will be the mainview and it will manage the game's logic
    private var gameView: GameView? = null
    private var opponents : OpponentUpdaterService? = null
    private var remotePlayer: PlayerUpdaterService? = null
    private var ghosts = listOf<Ghost>();
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

        val velocity = 4f;
        if(MultiplayerSession.player!= null) {
            ghosts = IntStream
                    .range(0, MultiplayerSession.opponents.size)
                    .mapToObj{ Ghost(LinearPositionStrategy(), velocity) }
                    .collect(Collectors.toList())
            opponents = OpponentUpdaterService(MultiplayerSession.player!!, MultiplayerSession.opponents.zip(ghosts))
            opponents?.start()
            Log.e("debug", "PARTITO THREAD OPPONENTS")
        }


        gameView = GameView(this, velocity, ghosts)
        gameView!!.addGameOverListener { score ->
            callGameOverActivity(score)
        }
        if(MultiplayerSession.player != null) {
            remotePlayer = PlayerUpdaterService(gameView!!.getPlayer(), MultiplayerSession.player!!);
            remotePlayer?.start()
            Log.e("debug", "PARTITO THREAD PLAYER")
        }

        setContentView(gameView)

    }
    /**
     * Calls the GameOverActivity to finish the game and report the score
     * @param score : the player's score at the end of the game.
     * @see GameOverActivity
     */
    private fun callGameOverActivity(score : Long) {
        remotePlayer?.setAsDead(ghosts.count { el -> el.dead })
        opponents?.stopUpdates()
        val intent = Intent(this, GameOverActivity::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause()
    }

    override fun onStop() {
        super.onStop()
        remotePlayer?.setAsDead(ghosts.count { el -> el.dead })
        opponents?.stopUpdates()
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }
}