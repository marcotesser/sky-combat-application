package com.skycombat.setting

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.skycombat.R

class GameSettingsService (private val act: Activity){
    companion object{
        const val VOLUME_KEY = "game-song-volume"
        const val VOLUME_MAX = 10
        const val GHOST_KEY = "game-ghost"
    }
     private val sharedPref : SharedPreferences by lazy {
         act.getSharedPreferences("game-settings", Context.MODE_PRIVATE)
     }

    var songVolume: Int
        get() {
            return sharedPref.getInt(VOLUME_KEY, VOLUME_MAX)
        }
        set(value){
            with (sharedPref.edit()) {
                putInt(VOLUME_KEY, value.coerceAtMost(VOLUME_MAX))
                apply()
            }
        }

    var ghostVisibility : Boolean
        get() : Boolean{
            return sharedPref.getBoolean(GHOST_KEY, true)
        }
        set(value){
            with (sharedPref.edit()) {
                putBoolean(GHOST_KEY, value)
                apply()
            }
        }
}