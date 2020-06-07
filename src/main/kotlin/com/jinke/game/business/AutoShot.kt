package com.jinke.game.business

import com.jinke.game.model.Model

/**
 * 自动射击的功能
 */
interface AutoShot :Model{
    /**
     * 自动射击
     */
    fun autoShot():Model?
}