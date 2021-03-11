package com.skycombat.game.model.gui.element.bullet

import com.skycombat.game.scene.ViewContext
import com.skycombat.game.model.geometry.Entity
import com.skycombat.game.model.gui.element.GUIElement
import com.skycombat.game.model.gui.element.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.gui.properties.HasHealth

/**
 * Represents a bullet
 * @param x : axis x coordinate of the bullet
 * @param y : axis y coordinate of the bullet
 * @param direction : direction of the bullet
 */
abstract class Bullet(var x : Float, var y : Float, var collisionStrategy: CollisionStrategy, private val direction: Direction)
    : GUIElement, Entity {

    enum class Direction{
        UP {
            override fun apply(delta: Float) : Float {
                return delta * -1
            }
        }, DOWN {
            override fun apply(delta: Float) : Float {
                return delta
            }
        };
        abstract fun apply(delta: Float) : Float
    }

    val context: ViewContext = ViewContext.getInstance()

    private var isHit: Boolean = false

    /**
     * Updates the bullet's movement
     */
    override fun update() {
        y += direction.apply(getSpeed())
    }

    /**
     * Removes if hit gets object out of context
     * @return isHit || this.y < 0 || this.y > scene.getMaxHeight()
     */
    override fun shouldRemove(): Boolean {
        return isHit || this.y < 0 || this.y > context.getHeightScreen()
    }

    abstract fun getDamage(): Float
    abstract fun getSpeed(): Float

    fun applyCollisionEffects(entityHitted: HasHealth) {
        if (collisionStrategy.shouldCollide(entityHitted)) {
            isHit = true
            entityHitted.updateHealth(-getDamage())
        }
    }

}


/*
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readFloat(),
        TODO("target")
    ){
        isHit = parcel.readByte() != 0.toByte()
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(x)
        parcel.writeFloat(y)
        parcel.writeFloat(getSpeed())
        parcel.writeFloat(getDamage())
        parcel.writeByte(if (isHit) 1 else 0)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Bullet> {
        override fun createFromParcel(parcel: Parcel): Bullet {
            return Bullet(parcel)
        }
        override fun newArray(size: Int): Array<Bullet?> {
            return arrayOfNulls(size)
        }
    }
 */