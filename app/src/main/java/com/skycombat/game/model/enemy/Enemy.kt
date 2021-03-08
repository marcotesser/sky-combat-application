package com.skycombat.game.model.enemy

import android.graphics.*
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.Player
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.Weapon
import com.skycombat.game.model.bullet.Bullet
import com.skycombat.game.model.bullet.strategy.EnemyCollisionStrategy
import com.skycombat.game.model.bullet.strategy.PlayerCollisionStrategy
import com.skycombat.game.model.component.EnemyHealthBar
import com.skycombat.game.model.component.HealthBar
import com.skycombat.game.model.event.ShootObserver
import com.skycombat.game.model.event.ShootListener
import com.skycombat.game.model.support.CanShoot
import com.skycombat.game.model.support.GUIElement
import com.skycombat.game.model.support.Rectangle
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
abstract class Enemy(var left : Float, var top : Float, bulletType: Weapon.BulletType)
    : HasHealth, Rectangle, GUIElement, CanShoot {
    var paint : Paint = Paint();

    var healthBar : HealthBar;
    var context: ViewContext = ViewContext.getInstance()
    override var shootObserver = ShootObserver()
    override var weapon:Weapon = Weapon(this, bulletType, EnemyCollisionStrategy())

    override var health : Float = getMaxHealth()
    var verticalAttitude: Int =1
    var horizontalAttitude: Int =1

    init {
        paint.color = Color.RED
        healthBar = EnemyHealthBar(this);
    }
    /**
     * Draws the player and enemy's healthbar
     * @param canvas : the canvas onto which the enemy will be drawn
     * @see HealthBar
     */
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(RectF(left, top, left + getWidth(), top+getHeight()), paint)
        healthBar.draw(canvas)
    }
    /**
     * Update bullets to the enemy
     * @param bullets : the bullets the enemy has shot
     * @see Bullet
     * @see HealthBar
     */
    override fun update() {

        MovHandle()

        healthBar.update()
    }

    abstract fun MovHandle()

    abstract fun getWidth():Float

    abstract fun getHeight():Float

    override fun shouldRemove(): Boolean {
        return isDead()
    }

    override fun getPosition(): RectF {
        return RectF(left, this.top, this.left + getWidth() , this.top + getHeight())
    }

    override fun startPointOfShoot(): PointF {
        return PointF(getPosition().centerX(), top + getHeight() + 2F)
    }
}