package com.skycombat

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skycombat.LobbyActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.ApiOperation
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.api.graphql.model.ModelSubscription
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.core.model.temporal.Temporal.Timestamp.now
import com.amplifyframework.datastore.generated.model.GameRoom
import com.amplifyframework.datastore.generated.model.Player
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var players : ArrayList<Player> = ArrayList<Player>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)
        findViewById<ImageButton>(R.id.singleplayer).setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
        //sono stati nascosti perchè inutili per l'app finale, non eliminati perchè magari utili per dev
            findViewById<Button>(R.id.registrazione).visibility = View.GONE
            findViewById<Button>(R.id.play).visibility = View.GONE
            findViewById<Button>(R.id.changescore).visibility = View.GONE
            findViewById<Button>(R.id.add_to_lobby).visibility = View.GONE

        // crea stanza e ci associa un giocatore
        findViewById<ImageButton>(R.id.multiplayer).setOnClickListener {
            Log.i("idk", AWSMobileClient.getInstance().tokens.idToken.tokenString)

            val url = "https://kqkytn0s9f.execute-api.eu-central-1.amazonaws.com/V1/add-to-pendency"
            val queue  = Volley.newRequestQueue(this)
            val jsonObjectRequest = object: JsonObjectRequest(Request.Method.GET, url, null,
                    { response ->
                        if(response.has("id")){
                            var intent = Intent(this, LobbyActivity::class.java)
                            intent.putExtra("id-player", response.getString("id"))
                            startActivity(intent)
                        }
                    },
                    { error ->
                        Log.e("errore", error.toString())
                        toast("Errore richiesta partita")
                    }
            ) {
                override fun getHeaders():Map<String, String> {
                    val params = HashMap<String, String>()
                    params["Authorization"] = AWSMobileClient.getInstance().tokens.idToken.tokenString
                    return params
                }
            }
            queue.add(jsonObjectRequest)
        }


        findViewById<ImageButton>(R.id.showplayers).setOnClickListener {
            startActivity(Intent(this, LeaderboardsActivity::class.java))
        }


        // aggiorna il proprio punteggio
        /*findViewById<Button>(R.id.changescore).setOnClickListener {
            var old = players.stream().filter{ p-> p.name.toLowerCase(Locale.ROOT).indexOf(Amplify.Auth.currentUser.username.toLowerCase()) != -1}.findFirst().get()
            Log.i("ID","${old.name},${old.id}")
            var player = Player.builder()
                    .name(old.name)
                    .id(old.id)
                    .gameroom(old.gameroom)
                    .score((Math.random() * 10000).toInt())
                    .lastinteraction(Temporal.Timestamp.now())
                    .build()
            Amplify.API.mutate(
                    ModelMutation.update(player),
                    { response -> Log.i("MyAmplifyApp", "Todo with id: " + response.data.id) },
                    { error -> Log.e("MyAmplifyApp", "Create failed", error) }
            )
        }*/


        // ottieni i giocatori e una volta ottenuti, ascoltare le loro modifiche
        runOnUiThread{
            Amplify.API.query(
                    ModelQuery.list(Player::class.java),
                    { response ->
                        players = ArrayList(response.data.items.toList())
                        val subscription: ApiOperation<*>? = Amplify.API.subscribe(
                                ModelSubscription.onUpdate(Player::class.java),                         //_______________________
                                { Log.i("ApiQuickStart", "Subscription established") },
                                { onUpdate ->
                                    Log.i("test", onUpdate.toString())//----update.data() getPlayers()
                                    players.removeIf { p -> p.id == onUpdate.data.id }
                                    players.add(onUpdate.data)
                                    updateOutput()
                                },
                                { onFailure -> Log.e("ApiQuickStart", "Subscription failed", onFailure) },
                                { Log.i("ApiQuickStart", "Subscription completed") }
                        )
                    },
                    { error -> Log.e("MyAmplifyApp", "Query failure", error) }
            )

        }








        // ottieni i giocatori
        /*
        findViewById<Button>(R.id.showplayers).setOnClickListener{
            Amplify.API.query(
                    ModelQuery.list(Player::class.java),
                    { response ->
                        val output =
                                response.data.items.sortedBy { p -> p.name.toLowerCase() }.joinToString { p -> "\n\n ${p.name},  score :${p.score},  ultima interazione: ${p.lastinteraction}" }
                        runOnUiThread{
                            findViewById<TextView>(R.id.output).text = output
                        }
                    },
                    { error -> Log.e("MyAmplifyApp", "Query failure", error) }
            )
        }
         */

        findViewById<Button>(R.id.play).setOnClickListener {
            Amplify.Auth.fetchAuthSession({ el ->
                if (el.isSignedIn) {
                    val type = arrayOf("Single-Player", "Multi-Player")
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Modalità di gioco")
                    builder.setItems(type) { _, index ->
                        Log.i("scelta", type[index])
                    }
                    this.runOnUiThread {
                        builder.show()
                    }
                } else {
                    login()
                }
            }, {})
        }
        findViewById<Button>(R.id.registrazione).setOnClickListener{
            register()
        }
        findViewById<ImageButton>(R.id.login).setOnClickListener{ login() }
        findViewById<ImageButton>(R.id.logout).setOnClickListener{ logout() }
        findViewById<ImageButton>(R.id.settings).setOnClickListener{
            //startActivity(Intent(this, SettingsActivity::class.java))
        }
        updateUI();
    }
    // mostra i giocatori
    private fun updateOutput(){
        runOnUiThread{
            //findViewById<TextView>(R.id.output).text = players.sortedBy { p -> p.score }.joinToString { p -> "nome : ${p.name}, score :${p.score}\n" }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data)
        }
        updateUI();
    }
    private fun login(){
        Amplify.Auth.signInWithWebUI(
                this,
                { result ->
                    toast("Accesso effettuato correttamente")
                    updateUI()
                },
                { error -> Log.e("Error", error.toString()) }
        )

    }
    private fun register(){
        Amplify.Auth.signInWithWebUI(
                this,
                { result ->
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
    private fun updateUI(){    // DA CAPIRE PERCHE' VA IN EXCEPTION
        this.runOnUiThread {
            //try {
            if(Amplify.Auth.currentUser != null) {
                findViewById<ImageButton>(R.id.login).visibility = View.GONE
                //findViewById<Button>(R.id.registrazione).visibility = View.GONE
                findViewById<ImageButton>(R.id.logout).visibility = View.VISIBLE
                findViewById<ImageButton>(R.id.multiplayer).isEnabled=true
                //findViewById<Button>(R.id.play).visibility = View.VISIBLE
                //findViewById<Button>(R.id.add_to_lobby).visibility = View.VISIBLE
                findViewById<ImageButton>(R.id.showplayers).visibility = View.VISIBLE
                //findViewById<Button>(R.id.changescore).visibility = View.VISIBLE
                //findViewById<Button>(R.id.changescore).text = "Cambia score di ${Amplify.Auth.currentUser.username}"
            } else {
                findViewById<ImageButton>(R.id.logout).visibility = View.GONE
                findViewById<ImageButton>(R.id.login).visibility = View.VISIBLE
                findViewById<ImageButton>(R.id.multiplayer).isEnabled=false
                //findViewById<Button>(R.id.registrazione).visibility = View.VISIBLE
                findViewById<Button>(R.id.play).visibility = View.GONE
                findViewById<Button>(R.id.add_to_lobby).visibility = View.GONE
                //findViewById<ImageButton>(R.id.showplayers).visibility = View.GONE
                //findViewById<Button>(R.id.changescore).visibility = View.GONE
            }
            /*} catch (ex: Exception) {
                findViewById<ImageButton>(R.id.logout).visibility = View.VISIBLE
                findViewById<ImageButton>(R.id.login).visibility = View.VISIBLE
                findViewById<Button>(R.id.registrazione).visibility = View.GONE

            }*/
        }
    }


}