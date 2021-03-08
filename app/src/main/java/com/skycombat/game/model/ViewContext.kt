package com.skycombat.game.model

class ViewContext{
    var width: Float
    var height: Float
    var resources: android.content.res.Resources
    companion object{
        private var instance: ViewContext? = null
        fun setContext(width: Float, height:Float, resources: android.content.res.Resources){
            if(instance==null) {
                instance = ViewContext(width, height, resources);
            }
        }
        fun getInstance():ViewContext{
            return instance!!;
        }
    }
    private constructor(width: Float, height: Float, resources: android.content.res.Resources){
        this.width=width
        this.height=height
        this.resources = resources
    }

    fun getWidthScreen():Float{
        return width
    }
    fun getHeightScreen():Float{
        return height
    }

    @JvmName("getResources1")
    fun getResources():android.content.res.Resources{
        return resources
    }
}