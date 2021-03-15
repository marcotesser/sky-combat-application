package com.skycombat.api.leaderboard
import org.json.JSONObject
class Result(obj : JSONObject) {
    var username : String? = null;
    var score : Long? = null;
    var defeated : Long? = null;
    init{
        if(obj.has("username")){
            username = obj.getString("username")
        }
        if(obj.has("score")){
            score = obj.getLong("score")
        }
        if(obj.has("defeated")){
            defeated = obj.getLong("defeated")
        }
    }
}