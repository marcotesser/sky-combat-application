package com.skycombat.api.leaderboard
import org.json.JSONObject
class Me (obj : JSONObject){
    var username : String? = null;
    var score : Long? = null;
    var defeated : Long? = null;
    var pos : Position;
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
        pos = Position(obj.getJSONObject("pos"))
    }
}