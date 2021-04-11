package com.skycombat.game.multiplayer

import com.amplifyframework.datastore.generated.model.Player
import java.util.concurrent.CopyOnWriteArrayList


class MultiplayerSession{
    var player: Player? = null
    var opponents: CopyOnWriteArrayList<Player> = CopyOnWriteArrayList()
    fun reset() : MultiplayerSession{
        this.player = null
        this.opponents = CopyOnWriteArrayList()
        return this
    }

    fun set(
        player: Player,
        opponents: CopyOnWriteArrayList<Player> = CopyOnWriteArrayList<Player>()
    ) : MultiplayerSession{
        this.player = player
        this.opponents = opponents
        return this
    }

    companion object {
        const val UPS = 5

        private val instance = MultiplayerSession()

        fun reset() : MultiplayerSession{
            instance.player = null
            instance.opponents = CopyOnWriteArrayList()
            return instance
        }

        fun set(
            player: Player,
            opponents: CopyOnWriteArrayList<Player> = CopyOnWriteArrayList<Player>()
        ) : MultiplayerSession{
            instance.player = player
            instance.opponents = opponents
            return instance
        }
        fun get() : MultiplayerSession {
            return instance
        }
    }
}
