package com.skycombat.api.leaderboard

import org.json.JSONObject

class Position (obj: JSONObject){
    var score : Long? = null;
    var defeated : Long? = null;
    init {
        if(obj.has("score")){
            score = obj.getLong("score")
        }
        if(obj.has("defeated")){
            defeated = obj.getLong("defeated")
        }
    }
}