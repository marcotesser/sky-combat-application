package com.skycombat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.amplifyframework.api.ApiOperation
import com.amplifyframework.api.graphql.model.ModelSubscription
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Player
import com.skycombat.game.multiplayer.MultiplayerSession
import java.util.concurrent.CopyOnWriteArrayList

class LobbyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        MultiplayerSession.player = null
        MultiplayerSession.opponents.clear()

        val idPlayer = intent.getStringExtra("id-player")
        if(idPlayer != null) {
            var sub: ApiOperation<*>? = null;
sub = Amplify.API.subscribe(
        ModelSubscription.onCreate(Player::class.java),
        { Log.i("ApiQuickStart", "Subscription established") },
        { onCreated ->
            if (onCreated.data.id.equals(idPlayer)) {
                MultiplayerSession.player = onCreated.data;
                MultiplayerSession.opponents = CopyOnWriteArrayList(onCreated.data.gameroom.players.filter {
                    op -> op.id != onCreated.data.id
                })
                startGameIfReady()
            } else {
                if (MultiplayerSession.player != null && onCreated.data.gameroom.id == MultiplayerSession.player!!.gameroom.id) {
                    MultiplayerSession.opponents.add(onCreated.data)
                    startGameIfReady()
                }
            }
        },
        { onFailure -> Log.e("ApiQuickStart", "Subscription failed", onFailure) },
        { Log.i("ApiQuickStart", "Subscription completed") }
)
        }
    }
     private fun startGameIfReady(){
        if(MultiplayerSession.player != null && MultiplayerSession.opponents.size == MultiplayerSession.player!!.gameroom.gamers - 1){
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}