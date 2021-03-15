/**
 * Project:  EverBuilds
 * File:  Gameloop.kt
 * Author:  Alberto Sinigaglia
 * Created:  2021-02-04
 * Version:  1.0.0
 * ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
 * Copyright 2021 EverBuild Group.
 * Licensed under the MIT License.  See License.txt in the project root for license information.
 * ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
 *
 */

package com.skycombat.game.scene

import android.graphics.Canvas
import android.view.SurfaceHolder

/**
 * Represents the Game Loop
 * @param game : the gameview onto which the game will be drawn
 * @param surfaceHolder : the base of the gameview
 */
class GameLoop(var game: GameView, var surfaceHolder: SurfaceHolder) : Thread() {
    companion object{
        private const val MAX_UPS: Double = 60.0
        private const val UPS_PERIOD: Double = 1000 / MAX_UPS
    }
    private var isRunning = false;
    private var averageUPS : Double = 0.0;
    private var averageFPS : Double = 0.0;
    /**
     * Gets the averageUPS
     * @return averageUPS
     */
    @JvmName("getAverageUPS1")
    fun getAverageUPS(): Double {
        return averageUPS;
    }
    /**
     * Gets the averageFPS
     * @return averageFPS
     *
     */
    @JvmName("getAverageFPS1")
    fun getAverageFPS(): Double {
        return averageFPS;
    }
    /**
     * Starts the loop
     */
    fun startLoop() {
        isRunning = true;
        start();
    }
    /**
     * Stops the loop
     * @throws InterruptedException if join() fails
     */
    fun stopLoop() {
        isRunning = false
        try {
            join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun run() {
        super.run()
        var canvas : Canvas? = null;
        var frameCount = 0
        var updateCount = 0
        var startTime: Long = System.currentTimeMillis()
        var elapsedTime: Long = 0
        var sleepTime: Long = 0
        while(isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    game.update();
                    updateCount++
                    game.draw(canvas)
                }
            } catch (ex: IllegalArgumentException){
                ex.printStackTrace()
            } finally {
                try {
                    if(canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                        frameCount++
                    }
                }catch (ex: Exception){
                    ex.printStackTrace()
                }
            }



            // avoid to exceed max UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (updateCount * UPS_PERIOD).toLong() - elapsedTime
            if(sleepTime > 0) {
                try{
                    sleep(sleepTime)
                } catch (ex: InterruptedException) {
                    ex.printStackTrace()
                }
            }

            //skip frames to mantain stable UPS
            while(sleepTime < 0 && updateCount < MAX_UPS-1 && isRunning) {
                game.update()
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (updateCount * UPS_PERIOD).toLong() - elapsedTime
            }



            if(elapsedTime > 1000) {
                averageUPS = updateCount.toDouble() / (1E-3 * elapsedTime)
                averageFPS = frameCount.toDouble() / (1E-3 * elapsedTime)
                frameCount = 0;
                updateCount = 0;
                startTime = System.currentTimeMillis()
            }
        }
    }
}