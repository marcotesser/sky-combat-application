package com.skycombat.game.model.gui.element.enemy

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.RectF
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
import com.skycombat.game.scene.ViewContext

/**
 * Represents an Enemy
 */
abstract class Enemy(bulletType: Weapon.BulletType, val movement: Movement)
    : HasHealth, Rectangle, GUIElement, CanShoot {
    abstract var enemyImg : Bitmap
    var left: Float = -100f
    var top: Float = -100f
    var points : Long = 100;
    var context: ViewContext = ViewContext.getInstance()
    override var shootObservable = ShootObservable()
    override var weapon: Weapon = Weapon(this, bulletType, EnemyCollisionStrategy(), Bullet.Direction.DOWN)
    var healthBar : HealthBar = EnemyHealthBar(this)

    override var health : Float = this.getMaxHealth()


    /**
     * Draws the player and enemy's healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(enemyImg, left, top,null)
        healthBar.draw(canvas)
    }

    /**
     * Update bullets to the enemy
     */
    override fun update() {

        movement.move(this)

        healthBar.update()

        weapon.update()
    }

    abstract fun getWidth():Float

    abstract fun getHeight():Float

    override fun shouldRemove(): Boolean {
        return isDead() || left < -150f || top > context.getHeightScreen()
                || left > context.getWidthScreen()|| top <-150f
    }

    override fun getPosition(): RectF {
        return RectF(left, top, left + getWidth() , top + getHeight())
    }

    override fun startPointOfShoot(): PointF {
        return PointF(getPosition().centerX(), top + getHeight() + 2F)
    }


}