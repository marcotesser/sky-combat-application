/**
 * Project:  EverBuilds
 * File:  GameOverListener.kt
 * Author:  Samuele Sartor
 * Created:  2021-02-15
 * Version:  1.0.0
 * ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
 * Copyright 2021 EverBuild Group.
 * Licensed under the MIT License.  See License.txt in the project root for license information.
 * ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
 *
 */

package com.skycombat.game

/**
 * Represents the Game Over Listener
 */
abstract class GameOverListener {

     /**
      * Abstract function
      * @param score : the player's final score
      */
     abstract fun gameOver( score: Long);
}