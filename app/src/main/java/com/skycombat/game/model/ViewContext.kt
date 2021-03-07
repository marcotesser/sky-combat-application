package com.skycombat.game.model

class ViewContext{
    var width: Float
    var height: Float
    companion object{
        private var instance: ViewContext? = null
        fun setContext(width: Float, height:Float){
            if(instance==null) {
                instance = ViewContext(width, height);
            }
        }
        fun getInstance():ViewContext{
            return instance!!;
        }
    }
    private constructor(width: Float, height: Float){
        this.width=width
        this.height=height
    }

    fun getWidthScreen():Float{
        return width
    }
    fun getHeightScreen():Float{
        return height
    }
}