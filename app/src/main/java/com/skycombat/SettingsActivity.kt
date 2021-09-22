package com.skycombat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Switch
import com.skycombat.setting.GameSettingsService

class SettingsActivity : AppCompatActivity() {
    private val gameSettings = GameSettingsService(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_settings)
        val ghostSwitch = findViewById<Switch>(R.id.ghost_switch)
        ghostSwitch.isChecked = gameSettings.ghostVisibility
        ghostSwitch.setOnCheckedChangeListener { _, isChecked ->
            gameSettings.ghostVisibility = isChecked
        }

        val tutorial = findViewById<ImageButton>(R.id.tutorialButton)
        tutorial.setOnClickListener{
            startActivity(Intent(this, TutorialActivity::class.java))
        }


        val volumeSeekBar = findViewById<SeekBar>(R.id.volume_seekBar)
        volumeSeekBar.progress = gameSettings.songVolume
        volumeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                gameSettings.songVolume = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                 // TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                 // TODO("Not yet implemented")
            }

        })
    }
}