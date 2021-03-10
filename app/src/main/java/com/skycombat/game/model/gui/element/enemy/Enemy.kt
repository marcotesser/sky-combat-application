package com.skycombat.game.model.gui.element.enemy

import android.graphics.*
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.geometry.Rectangle
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.component.EnemyHealthBar
import com.skycombat.game.model.gui.component.HealthBar
import com.skycombat.game.model.gui.element.GUIElement
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.strategy.EnemyCollisionStrategy
import com.skycombat.game.model.gui.element.enemy.movement.Movement
import com.skycombat.game.model.gui.event.ShootObservable
import com.skycombat.game.model.gui.properties.CanShoot
import com.skycombat.game.model.gui.properties.HasHealth

/**
 * Represents an Enemy
 */
abstract class Enemy(bulletType: Weapon.BulletType, movement: Movement)
    : HasHealth, Rectangle, GUIElement, CanShoot {
    abstract var enemyImg : Bitmap
    var healthBar : HealthBar = EnemyHealthBar(this)
    var context: ViewContext = ViewContext.getInstance()
    override var shootObservable = ShootObservable()
    override var weapon: Weapon = Weapon(this, bulletType, EnemyCollisionStrategy(), Bullet.Direction.DOWN)
    var mov=movement

    override var health : Float = this.getMaxHealth()


    /**
     * Draws the player and enemy's healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(enemyImg,mov.left,mov.top,null)
        healthBar.draw(canvas)
    }

    /**
     * Update bullets to the enemy
     */
    override fun update() {

        mov.move(this)

        healthBar.update()

        weapon.update()
    }

    abstract fun getWidth():Float

    abstract fun getHeight():Float

    override fun shouldRemove(): Boolean {
        return isDead() || mov.left < -150f || mov.top > context.getHeightScreen()
                || mov.left > context.getWidthScreen()|| mov.top <-150f
    }

    override fun getPosition(): RectF {
        return RectF(mov.left, mov.top, mov.left + getWidth() , mov.top + getHeight())
    }

    override fun startPointOfShoot(): PointF {
        return PointF(getPosition().centerX(), mov.top + getHeight() + 2F)
    }


}