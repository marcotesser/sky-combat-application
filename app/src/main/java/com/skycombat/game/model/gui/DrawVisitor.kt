package com.skycombat.game.model.gui

import android.graphics.Canvas
import com.skycombat.game.model.gui.component.Background
import com.skycombat.game.model.gui.component.HealthBar
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.bullet.*
import com.skycombat.game.model.gui.element.enemy.Enemy
import com.skycombat.game.model.gui.element.ghost.Ghost
import com.skycombat.game.model.gui.element.powerup.PowerUp
import com.skycombat.game.model.gui.panel.FPSPanel
import com.skycombat.game.model.gui.panel.UPSPanel

interface DrawVisitor {
    fun draw(canvas: Canvas?, powerUp: PowerUp)
    fun draw(canvas: Canvas?, ghost: Ghost)
    fun draw(canvas: Canvas?, player: Player)
    fun draw(canvas: Canvas?, background: Background)
    fun draw(canvas: Canvas?, gamePanel: FPSPanel)
    fun draw(canvas: Canvas?, gamePanel: UPSPanel)
    fun draw(canvas: Canvas?, healthBar: HealthBar)
    fun draw(canvas: Canvas?, enemy: Enemy)
    
    
    fun draw(canvas: Canvas?, bullet: ClassicBullet)
    fun draw(canvas: Canvas?, bullet: GustBullet)
    fun draw(canvas: Canvas?, bullet: LaserBullet)
    fun draw(canvas: Canvas?, bullet: MultipleBullet)
}