package com.skycombat.game.multiplayer

import com.amplifyframework.datastore.generated.model.Player
import java.util.concurrent.CopyOnWriteArrayList

class MultiplayerSession {
    companion object {
        var UPS = 5
        var player: Player? = null
        var opponents: CopyOnWriteArrayList<Player> = CopyOnWriteArrayList()
    }
}