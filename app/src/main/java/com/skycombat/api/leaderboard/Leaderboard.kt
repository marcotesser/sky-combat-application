package com.skycombat.api.leaderboard
import org.json.JSONObject

class Leaderboard(obj : JSONObject){
    var me: Me? = null
    var results : ArrayList<Result> = ArrayList();
    init{
        if(obj.has("me")){
            me = Me(obj.getJSONObject("me"))
        }
        if(obj.has("leaderboard")){
            val resultsLeaderboard = obj.getJSONArray("leaderboard");
            for (index in 0 until resultsLeaderboard.length()) {
                results.add(
                    Result(resultsLeaderboard.getJSONObject(index))
                )
            }
        }
    }

}