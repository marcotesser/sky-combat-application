package com.skycombat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.skycombat.game.model.gui.DisplayDimension
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.ghost.Ghost
import com.skycombat.game.model.gui.element.ghost.movement.LinearAimedPositionMovement
import com.skycombat.game.multiplayer.MultiplayerSession
import com.skycombat.game.multiplayer.OpponentsUpdaterService
import com.skycombat.game.multiplayer.RemotePlayerUpdaterService
import com.skycombat.game.multiplayer.RemoteOpponentUpdaterService
import com.skycombat.game.scene.GameView
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
    private var opponentsUpdater : OpponentsUpdaterService? = null
    private var remoteRemotePlayer: RemotePlayerUpdaterService? = null
    private var currentGametype : GAMETYPE = GAMETYPE.SINGLE_PLAYER
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var player : Player
    private var score = 0L

    override fun onCreate(savedInstanceState: Bundle?) {

        // impostare l'activity
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mediaPlayer = MediaPlayer.create(this, R.raw.game_song)

        // ottenere dimensioni schermo
        val metrics = DisplayMetrics()
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(metrics)
        val displayDimension = DisplayDimension(
                metrics.widthPixels.toFloat(),
                metrics.heightPixels.toFloat()
        )

        val session = MultiplayerSession.get()


        // creazione GameView
        player = Player(velocity, LinearAimedPositionMovement(), displayDimension)
        player.addOnDeathOccurListener{
            score = when(currentGametype){
                GAMETYPE.MULTI_PLAYER -> getCountDeadOpponents()
                GAMETYPE.SINGLE_PLAYER -> gameView?.deadEnemies?.map { enemy ->
                    enemy.points
                }?.reduceOrNull(Long::plus) ?: 0L

            }
            remoteRemotePlayer?.setAsDead(getCountDeadOpponents().toInt())
        }
        gameView = GameView(
            this,
            displayDimension,
            player,
            seed = session.player?.gameroom?.seed?.toLong() ?: Random.nextLong()
        )
        gameView!!.addGameOverListener {
            callGameOverActivity()
        }



        // creazione dinamiche multiplayer
        val ghosts: CopyOnWriteArrayList<Ghost>


        //creazione del FrameLayout per contenere gameView e il bottone di uscita precoce
        val fw = FrameLayout(this)
        fw.addView(gameView)

        if(session.player != null) {
            currentGametype = GAMETYPE.MULTI_PLAYER

            // opponenti
            ghosts = CopyOnWriteArrayList(IntStream
                .range(0, session.opponents.size)
                .mapToObj{ Ghost(LinearAimedPositionMovement(), velocity, displayDimension) }
                .collect(Collectors.toList()))
            opponentsUpdater = RemoteOpponentUpdaterService(
                session.player!!,
                session.opponents.zip(ghosts)
            )
            opponentsUpdater?.start()

            // player corrente
            remoteRemotePlayer = RemotePlayerUpdaterService(gameView!!.getPlayer(), session.player!!)
            remoteRemotePlayer?.start()

            gameView?.setGhosts(ghosts)

            // listener per l'uscita precoce solo in multiplayer
            player.addOnDeathOccurListener {
                showQuit()
            }
            val overlay : ConstraintLayout = LayoutInflater.from(this).inflate(R.layout.activity_game_quit, fw, false) as ConstraintLayout

            fw.addView(overlay)

            // imposta onClickListener per uscire prima dalla partita multiplayer
            val quitButton = overlay.findViewById<ImageButton>(R.id.quit)
            quitButton.setOnClickListener{
                this.callGameOverActivity()
                this.finish()
            }
            quitButton.visibility = View.GONE

        } else {
            currentGametype = GAMETYPE.SINGLE_PLAYER
            ghosts = CopyOnWriteArrayList()

            gameView?.setGhosts(ghosts)

            /*
            // serve solo per simulare il multiplayer in locale utilizzato la modalità "singleplayer" del gioco
            currentGAMETYPE = GAMETYPE.MULTI_PLAYER
            ghosts = CopyOnWriteArrayList(IntStream
                    .range(0, 4)
                    .mapToObj{ Ghost(LinearAimedPositionMovement(), velocity) }
                    .collect(Collectors.toList()))
            opponentsUpdater = LocalOpponentsUpdaterService(ghosts)
            opponentsUpdater?.start()
            */
        }

        //toglie l'allocazione sulla GUI del bottone cosi` puo` riapparire sopra la gameView
        setContentView(fw)
        mediaPlayer.isLooping = true;
        mediaPlayer.start()

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
        val intent = Intent(this, GameOverActivity::class.java)

        // in caso ci fossero servizi di aggiornamento remoti, li fermo
        gameView?.pause()
        remoteRemotePlayer?.setAsDead(getCountDeadOpponents().toInt())
        opponentsUpdater?.stopUpdates()

        mediaPlayer.stop()
        intent.putExtra(SIGLA_TYPE, currentGametype)
        intent.putExtra(SIGLA_SCORE, score)

        startActivity(intent)
    }


    private fun showQuit() {
        runOnUiThread{
            findViewById<ImageButton>(R.id.quit)?.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.release()
        remoteRemotePlayer?.setAsDead(getCountDeadOpponents().toInt())
        opponentsUpdater?.stopUpdates()
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }
}