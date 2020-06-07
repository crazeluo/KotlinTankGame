package com.jinke.game.business

import com.jinke.game.enums.Direction
import com.jinke.game.model.Model

interface AutoMove:Model{
    /**
     * 移动速度
     */
    val speed:Int

    /**
     * 移动方向
     */
    val currentDirection:Direction
    /**
     * 自动移动
     */
    fun autoMove()

}