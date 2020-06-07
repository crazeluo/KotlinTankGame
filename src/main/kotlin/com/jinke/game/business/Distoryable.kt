package com.jinke.game.business

import com.jinke.game.model.Model

/**
 * 销毁的能力
 */
interface Distoryable : Model{
    /**
     * 是否被销毁
     */
    fun isDistory():Boolean

    fun showDistory():Array<Model>?{
        return null
    }
}