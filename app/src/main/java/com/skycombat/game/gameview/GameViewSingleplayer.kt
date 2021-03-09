package com.skycombat.game.gameview

import android.content.Context
import android.graphics.Canvas
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.enemy.Enemy
import com.skycombat.game.model.powerup.PowerUp
import com.skycombat.game.model.support.Drawable
import com.skycombat.game.model.support.GUIElement
import java.util.stream.Stream

class GameViewSingleplayer(context: Context, MAX_WIDTH : Float, MAX_HEIGHT : Float )
    : GameView(context, MAX_WIDTH, MAX_HEIGHT ) {

    override fun drawEntity(canvas: Canvas?) {
        if (canvas != null) {
            Stream.concat(
                Stream.of(player),
                Stream.of(panels, enemies, bullets, powerUps).flatMap(
                    List<Drawable>::stream
                )
            ).forEach { el -> el.draw(canvas) }
        }
    }

    override fun updateEntity(){

        listOf(enemies, powerUps, bullets).forEach { ar ->
            ar.removeIf(GUIElement::shouldRemove)
        }

        player.update()
        enemies.forEach(Enemy::update)
        bullets.forEach(Bullet::update)
        powerUps.forEach(PowerUp::update)
    }

}