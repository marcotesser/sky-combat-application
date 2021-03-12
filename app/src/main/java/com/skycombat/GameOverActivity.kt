package com.skycombat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_game_over)
        val score = intent.getLongExtra(GameActivity.SIGLA_SCORE, 0)
        findViewById<TextView>(R.id.score).text = score.toString()
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }
}