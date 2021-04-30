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
            1-> return "Accedi all’area autenticazione per mezzo del pulsante cerchiato, li potrai eseguire il login inserendo le tue credenziali.\n\nSe invece devi ancora registrarti seleziona il link: “Sign up” dell’area autenticazione.\n\nUna volta autenticato potrai accedere alla modalità multigiocatre."
            2-> return "Per visualizzare le classifiche ti basterà cliccare l’icona raffigurante una medaglia nella schermata principale.\n\nCliccando “giocatori sconfitti” potrai osservare la classifica delle partite multigiocatore, il pulsante “punteggio maggiore” invece ti mostrerà quella delle partite in giocatore singolo."
            3-> return "Potrai accedere al gioco scegliendo una delle due modalità disponibili identificate dai tasti cerchiati.\n\nL’obbiettivo del gioco può essere: rimanere in vita più tempo possibile (giocatore e singolo) oppure sconfiggere il più alto numero di nemici (multigiocatore).\n\nPer interagire con il gioco ti basterà muovere trascinare la tua navicella orizzontalmente sullo schermo, e non preoccuparti.. lo sparo è automatico!"
            4-> return "La partita multigiocatore comincerà quando saranno presenti nella sala d'attesa tra i 2 e i 6 giocatori.\n\nGli avversari saranno rappresentati da navicelle semitrasparenti e non avranno influenza sul tuo gioco.\n\nSe sei stato eliminato prima della fine partita potrai tornare alla schermata principale cliccando il pulsante “esci”."
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