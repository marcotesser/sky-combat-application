package com.skycombat.game.model.gui.element.enemy

import android.graphics.*
import com.skycombat.game.model.gui.properties.HasHealth
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.gui.Weapon
import com.skycombat.game.model.gui.element.bullet.Bullet
import com.skycombat.game.model.gui.element.bullet.strategy.EnemyCollisionStrategy
import com.skycombat.game.model.gui.component.EnemyHealthBar
import com.skycombat.game.model.gui.component.HealthBar
import com.skycombat.game.model.gui.element.enemy.movement.Movement
import com.skycombat.game.model.gui.event.ShootObserver
import com.skycombat.game.model.gui.properties.CanShoot
import com.skycombat.game.model.gui.element.GUIElement
import com.skycombat.game.model.geometry.Rectangle
import java.util.*

/**
 * Represents an Enemy
 * @param player : used as a base to draw the enemy
 * @param left : movement towards left
 * @param top : movement towards the top
 * @param width : enemy's width
 * @param height : enemy's height
 * @param scene : the gameview onto which the enemy will be drawn
 */
abstract class Enemy(bulletType: Weapon.BulletType, movement: Movement)
    : HasHealth, Rectangle, GUIElement, CanShoot {
    abstract var enemyImg : Bitmap
    var healthBar : HealthBar;
    var context: ViewContext = ViewContext.getInstance()
    override var shootObserver = ShootObserver()
    override var weapon: Weapon = Weapon(this, bulletType, EnemyCollisionStrategy())
    var mov=movement

    override var health : Float = getMaxHealth()


    init {
        healthBar = EnemyHealthBar(this);
    }
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
     * @param bullets : the bullets the enemy has shot
     * @see Bullet
     * @see HealthBar
     */
    override fun update() {

        //MovHandle()

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