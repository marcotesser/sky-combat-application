package com.skycombat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.skycombat.game.multiplayer.MultiplayerSession
import java.util.concurrent.CopyOnWriteArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)
        val singleplayer = findViewById<ImageButton>(R.id.singleplayer)
        val multiplayer = findViewById<ImageButton>(R.id.multiplayer)
        val leaderboard = findViewById<ImageButton>(R.id.leaderboard)
        val login = findViewById<ImageButton>(R.id.login)
        val logout = findViewById<ImageButton>(R.id.logout)
        val settings = findViewById<ImageButton>(R.id.settings)


        // crea stanza e ci associa un giocatore
        multiplayer.setOnClickListener {
            toggleMultiplayer(false)
            val url = "https://kqkytn0s9f.execute-api.eu-central-1.amazonaws.com/V1/add-to-pendency"
            val queue  = Volley.newRequestQueue(this)
            val jsonObjectRequest = object: JsonObjectRequest(Method.GET, url, null,
                    { response ->
                        if(response.has("id")){
                            val intent = Intent(this, LobbyActivity::class.java)
                            intent.putExtra("id-player", response.getString("id"))
                            startActivity(intent)
                            toggleMultiplayer(true)
                        }
                    },
                    { error ->
                        toggleMultiplayer(true)
                        Log.e("errore", error.toString())
                        toast("Errore richiesta partita")
                    }
            ) {
                override fun getHeaders():Map<String, String> {
                    return mapOf("Authorization" to AWSMobileClient.getInstance().tokens.idToken.tokenString)
                }
            }
            queue.add(jsonObjectRequest)
        }


        login.setOnClickListener{ login() }
        logout.setOnClickListener{ logout() }
        settings.setOnClickListener{
            // TODO startActivity(Intent(this, SettingsActivity::class.java))
        }
        leaderboard.setOnClickListener {
            startActivity(Intent(this, LeaderboardsActivity::class.java))
        }
        singleplayer.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }


        updateUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        MultiplayerSession.player = null
        MultiplayerSession.opponents = CopyOnWriteArrayList()
    }

    private fun login(){
        Amplify.Auth.signInWithWebUI(
                this,
                {
                    toast("Accesso effettuato correttamente")
                    updateUI()
                },
                { error -> Log.e("Error", error.toString()) }
        )

    }
    private fun logout(){
        Amplify.Auth.signOut({
            toast("Logout effettuato correttamente")
            updateUI()
        },
                { error -> Log.e("Error", error.toString()) }
        )

    }
    private fun toast(text: String, size: Int = Toast.LENGTH_SHORT){
        this.runOnUiThread{
            val toast = Toast.makeText(this, text, size)
            toast.show()
        }
    }

    private fun updateUI(){
        val login = findViewById<ImageButton>(R.id.login)
        val logout = findViewById<ImageButton>(R.id.logout)
        this.runOnUiThread {
            if(Amplify.Auth.currentUser != null) {
                login.visibility = View.GONE
                logout.visibility = View.VISIBLE
                toggleMultiplayer(true)
            } else {
                logout.visibility = View.GONE
                login.visibility = View.VISIBLE
                toggleMultiplayer(false)
            }
        }
    }

    private fun toggleMultiplayer(status: Boolean){
        val multiplayer = findViewById<ImageButton>(R.id.multiplayer)
        multiplayer.isEnabled = status
        multiplayer.isClickable = status
    }
}