package com.skycombat.game.scene

import android.content.Context
import android.graphics.*
import com.skycombat.game.model.gui.DrawVisitor
import com.skycombat.game.model.gui.component.Background
import com.skycombat.game.model.gui.component.HealthBar
import com.skycombat.game.model.gui.element.Player
import com.skycombat.game.model.gui.element.bullet.ClassicBullet
import com.skycombat.game.model.gui.element.bullet.GustBullet
import com.skycombat.game.model.gui.element.bullet.LaserBullet
import com.skycombat.game.model.gui.element.bullet.MultipleBullet
import com.skycombat.game.model.gui.element.enemy.Enemy
import com.skycombat.game.model.gui.element.enemy.JetEnemy
import com.skycombat.game.model.gui.element.ghost.Ghost
import com.skycombat.game.model.gui.element.powerup.PowerUp
import com.skycombat.game.model.gui.panel.FPSPanel
import com.skycombat.game.model.gui.panel.UPSPanel

class GameViewDrawableVisitor(val context: Context): DrawVisitor {
    private val cache = HashMap<Int, Bitmap>()
    override fun draw(canvas: Canvas?, ghost: Ghost) {
        val ghostImg = cache.getOrPut(ghost.ghostImg, {
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources, ghost.ghostImg),
                Ghost.RADIUS.toInt() * 2,
                Ghost.RADIUS.toInt() * 2,
                false
            )
        })

        canvas?.drawBitmap(
            ghostImg,
            ghost.getX() - Ghost.RADIUS / 2,
            ghost.y - Ghost.RADIUS / 2,
            ghost.paint
        )
    }

    override fun draw(canvas: Canvas?, player: Player) {
        if (player.hasShield()) {
            val playerShieldImg = cache.getOrPut(player.playerShieldImg, {
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(context.resources, player.playerShieldImg),
                    Player.RADIUS.toInt() * 2,
                    Player.RADIUS.toInt() * 2,
                    false
                )
            })
            canvas ?. drawBitmap (
                playerShieldImg,
                player.getX() - Player.RADIUS / 2, player.getY() - Player.RADIUS / 2, null
            )
        } else {
            val playerImg = cache.getOrPut(player.playerImg, {
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(context.resources, player.playerImg),
                    Player.RADIUS.toInt() * 2,
                    Player.RADIUS.toInt() * 2,
                    false
                )
            })

            canvas?.drawBitmap(
                playerImg,
                player.getX() - Player.RADIUS / 2,
                player.getY() - Player.RADIUS / 2,
                null
            )
        }
    }

    override fun draw(canvas: Canvas?, background: Background) {
        val backgroundImage = cache.getOrPut(background.backgroundImage, {
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources, background.backgroundImage),
                background.displayDimension.width.toInt(),
                background.displayDimension.width.toInt() * 3,
                false
            )
        })

        canvas?.drawBitmap(backgroundImage, 0f, background.backY ,null)
        canvas?.drawBitmap(backgroundImage, 0f, background.backY - background.displayDimension.width.toInt()*3 ,null)

        val overlayImage = cache.getOrPut(background.overlayImage, {
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources, background.overlayImage),
                background.displayDimension.width.toInt(),
                background.displayDimension.width.toInt() * 3,
                false
            )
        })

        canvas?.drawBitmap(overlayImage, 0f, background.backY2 ,null)
        canvas?.drawBitmap(overlayImage, 0f, (background.backY2 - background.displayDimension.width.toInt()*3) ,null)
    }

    override fun draw(canvas: Canvas?, gamePanel: FPSPanel) {
        // TODO("Not yet implemented") -> inutile, sanno già disegnarsi da soli
    }

    override fun draw(canvas: Canvas?, gamePanel: UPSPanel) {
        // TODO("Not yet implemented") -> inutile, sanno già disegnarsi da soli
    }


    override fun draw(canvas: Canvas?, healthBar: HealthBar) {
        val paint = Paint()
        paint.color = Color.rgb(
            if(healthBar.percentage<0.5F) 1F else 1F-healthBar.percentage,
            if(healthBar.percentage<0.5F) healthBar.percentage else 1F, 0F
        )
        canvas?.drawRect(healthBar.life, paint)
    }

    override fun draw(canvas: Canvas?, enemy: Enemy) {
        val enemyImg = cache.getOrPut(enemy.enemyImg, {
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources, enemy.enemyImg),
                JetEnemy.WIDTH.toInt(),
                JetEnemy.HEIGHT.toInt(),
                false
            )
        })
        canvas?.drawBitmap(enemyImg, enemy.left, enemy.top,null)
    }

    override fun draw(canvas: Canvas?, bullet: ClassicBullet) {
        val bulletImg = cache.getOrPut(bullet.bulletImg, {
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources, bullet.bulletImg),
                bullet.getRadius().toInt() * 2,
                bullet.getRadius().toInt() * 2,
                false
            )
        })
        canvas?.drawBitmap(
            bulletImg,
            bullet.x-bullet.getRadius(),
            bullet.y-bullet.getRadius(),
            null
        )
    }

    override fun draw(canvas: Canvas?, bullet: GustBullet) {
        val bulletImg = cache.getOrPut(bullet.bulletImg, {
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources, bullet.bulletImg),
                bullet.getRadius().toInt() * 2,
                bullet.getRadius().toInt() * 2,
                false
            )
        })
        canvas?.drawBitmap(
            bulletImg,
            bullet.x-bullet.getRadius(),
            bullet.y-bullet.getRadius(),
            null
        )
    }

    override fun draw(canvas: Canvas?, bullet: LaserBullet) {
        val bulletImg = cache.getOrPut(bullet.bulletImg, {
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources, bullet.bulletImg),
                LaserBullet.WIDTH.toInt(),
                LaserBullet.HEIGHT.toInt(),
                false
            )
        })
        canvas?.drawBitmap(
            bulletImg,
            bullet.x- LaserBullet.WIDTH /2F,
            bullet.y- LaserBullet.HEIGHT /2F,
            null
        )
    }

    override fun draw(canvas: Canvas?, bullet: MultipleBullet) {
        val bulletImg = cache.getOrPut(bullet.bulletImg, {
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources, bullet.bulletImg),
                MultipleBullet.WIDTH.toInt(),
                MultipleBullet.HEIGHT.toInt(),
                false
            )
        })
        canvas?.drawBitmap(
            bulletImg,
            bullet.x- MultipleBullet.WIDTH /2F,
            bullet.y- MultipleBullet.HEIGHT /2F,
            null
        )
    }

    override fun draw(canvas: Canvas?, powerUp: PowerUp) {
        val img = cache.getOrPut(powerUp.powerUpImg, {
            Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.resources, powerUp.powerUpImg),
                PowerUp.RADIUS.toInt() * 2,
                PowerUp.RADIUS.toInt() * 2,
                false
            )
        })
        canvas?.drawBitmap(img,powerUp.x - powerUp.getRadius(),powerUp.y - powerUp.getRadius(),null)
    }

}