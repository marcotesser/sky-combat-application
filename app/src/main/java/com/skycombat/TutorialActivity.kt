package com.skycombat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class TutorialActivity : AppCompatActivity() {

    private var currentSlide:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_tutorial)

        adjustButtons()
        setSlide()

        findViewById<ImageButton>(R.id.nextButton).setOnClickListener{
            adjustIndex(1)
            adjustButtons()
            setSlide()
        }
        findViewById<ImageButton>(R.id.beforeButton).setOnClickListener{
            adjustIndex(-1)
            adjustButtons()
            setSlide()
        }
    }

    private fun adjustIndex(delta:Int){
        if(currentSlide + delta > 4) currentSlide=4
        if(currentSlide + delta < 1) currentSlide=1
        currentSlide += delta
    }

    private fun adjustButtons(){
        val next = findViewById<ImageButton>(R.id.nextButton)
        val before = findViewById<ImageButton>(R.id.beforeButton)

        if(currentSlide == 1){
            before.isEnabled=false
        }else if(currentSlide == 4){
            next.isEnabled=false
        }else{
            before.isEnabled=true
            next.isEnabled=true
        }
    }

    private fun setSlide() {
        runOnUiThread {
            findViewById<TextView>(R.id.slideText).text = getSlideText(currentSlide)
            findViewById<ImageView>(R.id.slideImage).setImageResource(getSlideImg(currentSlide))
        }
    }



    private fun getSlideText(numSlide: Int):String {
        when(numSlide){
            1-> return "Accedendo all’area autenticazione per mezzo del pulsante cerchiato si potranno inserire le proprie credenziali ed eseguire così il login.\n\nSempre nella stessa area sarà possibile selezionare il testo azzurro: “Sign up” che vi permetterà di accedere alla procedura di registrazione.\n\nL’esecuzione dell’autenticazione causerà l’attivazione del pulsante: “MULTI PLAYER” presente nella schermata iniziale."
            2-> return "Per visualizzare le classifiche è necessario cliccare l’icona raffigurante una medaglia nella schermata principale.\n\nCliccando “PLAYER DEFEATED” verrà mostrata all’utente la classifica relativa alle partite multiplayer, il pulsante “MAX SCORE” invece condurrà alla classifica delle partite in singleplayer"
            3-> return "Al gioco è possibile accedere scegliendo una delle due modalità disponibili identificate dai tasti cerchiati.\n\nL’obbiettivo del gioco dipende dal tipo di classifica associata alla modalità e può essere: rimanere in vita più tempo possibile oppure sconfiggere il più alto numero di nemici.\n\nIl solo mezzo di interazione con la realtà di gioco consiste nel poter muovere la propria navicella trasversalmente facendo scorrere il dito sullo schermo, lo sparo è automatico e continuativo."
            4-> return "Selezionando la modalità multi giocatore si dovrà attende per essere inseriti in una partita. Comincerà quando ci saranno tra i 2 e i 6 giocatori.\n\nGli avversari saranno rappresentati da navicelle semitrasparenti e non influenzano il gioco dell’utente.\n\nSe il giocatore non è l’ultimo ad essere eliminato potrà tornare alla schermata principale cliccando il pulsante “QUIT”."
            else -> return ""
        }
    }

    private fun getSlideImg(numSlide: Int):Int {
        return when(numSlide){
            1-> R.drawable.slide1
            2-> R.drawable.slide2
            3-> R.drawable.slide3
            4-> R.drawable.slide4
            else -> 0
        }
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }
}