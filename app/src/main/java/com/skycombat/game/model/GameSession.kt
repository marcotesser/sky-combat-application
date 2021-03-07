package com.skycombat.game.model

import com.amplifyframework.datastore.generated.model.GameRoom
import com.amplifyframework.datastore.generated.model.Player

class GameSession {
    companion object{
        var player : Player? = null
        var GameRoom : GameRoom? = null
        open var otherPlayers : ArrayList<Player>? = null
    }
}