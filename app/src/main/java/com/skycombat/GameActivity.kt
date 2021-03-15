package com.skycombat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.ghost.Ghost
import com.skycombat.game.model.gui.element.ghost.strategy.LinearPositionStrategy
import com.skycombat.game.multiplayer.MultiplayerSession
import com.skycombat.game.multiplayer.OpponentsUpdater
import com.skycombat.game.multiplayer.PlayerUpdaterService
import com.skycombat.game.multiplayer.RemoteOpponentUpdaterService
import com.skycombat.game.scene.GameView
import com.skycombat.game.scene.ViewContext
import java.io.Serializable
import java.util.concurrent.CopyOnWriteArrayList
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.random.Random

class GameActivity : Activity() {
    companion object{
        const val SIGLA_SCORE = "game-score"
        const val SIGLA_TYPE = "game-type"
    }
    enum class GAMETYPE : Serializable{
        SINGLE_PLAYER {
            override fun sigla(): String {
                return "single-player"
            }
        }, MULTI_PLAYER {
            override fun sigla(): String {
                return "multi-player"
            }
        };
        abstract fun sigla(): String
    }

    //gameView will be the mainview and it will manage the game's logic
    private val velocity = 4f
    private var gameView: GameView? = null
    private var opponentsUpdater : OpponentsUpdater? = null
    private var remotePlayer: PlayerUpdaterService? = null
    private var currentGametype : GAMETYPE = GAMETYPE.SINGLE_PLAYER
    private lateinit var player : Player
    private var score = 0L;
    override fun onCreate(savedInstanceState: Bundle?) {

        // impostare l'activity
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // ottenere dimensioni schermo
        val metrics = DisplayMetrics()
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(metrics)
        ViewContext.setContext(
            metrics.widthPixels.toFloat(),
            metrics.heightPixels.toFloat(),
            resources
        )
        Log.e("giocatori", MultiplayerSession.opponents.toString())


        // creazione GameView
        player = Player(velocity, LinearPositionStrategy())
        player.addOnDeathOccurListener{
            score = when(currentGametype){
                GAMETYPE.MULTI_PLAYER -> getCountDeadOpponents()
                GAMETYPE.SINGLE_PLAYER -> gameView?.deadEnemies?.map { enemy ->
                    enemy.points
                }?.reduceOrNull(Long::plus) ?: 0L
            }
            Log.e("MORTO PLAYER PRINCIPALE, LO SCORE è", score.toString())
            remotePlayer?.setAsDead(getCountDeadOpponents().toInt())
        }
        gameView = GameView(
            this,
            player,
            seed = MultiplayerSession.player?.gameroom?.seed?.toLong() ?: Random.nextLong()
        )
        gameView!!.addGameOverListener {
            callGameOverActivity()
        }



        // creazione dinamiche multiplayer
        val ghosts: CopyOnWriteArrayList<Ghost>

        if(MultiplayerSession.player != null) {
            currentGametype = GAMETYPE.MULTI_PLAYER

            // opponenti
            ghosts = CopyOnWriteArrayList(IntStream
                .range(0, MultiplayerSession.opponents.size)
                .mapToObj{ Ghost(LinearPositionStrategy(), velocity) }
                .collect(Collectors.toList()))
            opponentsUpdater = RemoteOpponentUpdaterService(
                MultiplayerSession.player!!,
                MultiplayerSession.opponents.zip(ghosts)
            )
            opponentsUpdater?.start()

            // gestiamo il player corrente
            remotePlayer = PlayerUpdaterService(gameView!!.getPlayer(), MultiplayerSession.player!!)
            remotePlayer?.start()

        } else {
            currentGametype = GAMETYPE.SINGLE_PLAYER
            ghosts = CopyOnWriteArrayList()

            /*
            // serve solo per simulare il multiplayer in locale utilizzato la modalità "singleplayer" del gioco
            currentGAMETYPE = GAMETYPE.MULTI_PLAYER
            ghosts = CopyOnWriteArrayList(IntStream
                    .range(0, 4)
                    .mapToObj{ Ghost(LinearPositionStrategy(), velocity) }
                    .collect(Collectors.toList()))
            opponentsUpdater = MockOpponentsUpdaterService(ghosts)
            opponentsUpdater?.start()
            */
        }
        gameView?.setGhosts(ghosts)
        setContentView(gameView)

    }

    private fun getCountDeadOpponents() : Long {
        if(player.isAlive()){
            return opponentsUpdater?.getOpponents()?.count { el ->
                el.isDead()
            }?.toLong() ?: 0L
        }
        return opponentsUpdater?.getOpponents()?.count { el ->
            el.isDead() &&
                    el.deadAt!! <
                    player.deadAt!!
        }?.toLong() ?: 0L
    }

    /**
     * Calls the GameOverActivity to finish the game and report the score
     * @see GameOverActivity
     */
    private fun callGameOverActivity() {
        Log.e("test", "chiamo game over activity")
        val intent = Intent(this, GameOverActivity::class.java)

        // in caso ci fossero servizi di aggiornamento remoti, li fermo
        remotePlayer?.setAsDead(getCountDeadOpponents().toInt())
        opponentsUpdater?.stopUpdates()

        intent.putExtra(SIGLA_TYPE, currentGametype)
        intent.putExtra(SIGLA_SCORE, score)

        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause()
    }

    override fun onStop() {
        super.onStop()
        remotePlayer?.setAsDead(getCountDeadOpponents().toInt())
        opponentsUpdater?.stopUpdates()
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }
}