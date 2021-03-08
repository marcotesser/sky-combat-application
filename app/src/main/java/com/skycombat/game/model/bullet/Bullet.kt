package com.skycombat.game.model.bullet

import android.graphics.*
import com.skycombat.R
import com.skycombat.game.model.HasHealth
import com.skycombat.game.model.ViewContext
import com.skycombat.game.model.bullet.strategy.CollisionStrategy
import com.skycombat.game.model.support.Entity
import com.skycombat.game.model.support.GUIElement

/**
 * Represents a bullet
 * @param x : axis x coordinate of the bullet
 * @param y : axis y coordinate of the bullet
 * @param direction : direction of the bullet
 * @param scene : the gameview onto which the bullet will be drawn
 */
abstract class Bullet(var x : Float, var y : Float, var collisionStrategy: CollisionStrategy)
    : GUIElement, Entity {

    val context: ViewContext = ViewContext.getInstance()
    var target = collisionStrategy.getTargetCollidable()

    private var isHit: Boolean = false;

    /**
     * Updates the bullet's movement
     */
    override fun update() {
        y += when (target) {
            CollisionStrategy.Target.ENEMY -> -getSpeed()
            CollisionStrategy.Target.PLAYER -> getSpeed()
        }
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