package com.jinke.game.business

import com.jinke.game.Config
import com.jinke.game.business.Blockabe
import com.jinke.game.enums.Direction
import com.jinke.game.model.Model

/**
 * 移动的能力
 */
interface Moveable : Model {
    /**
     * 可移动物体存在的移动方向
     */
    val currentDirection:Direction

    /**
     * 移动速度
     */
    val speed:Int
    /**
     * 运动的物体和阻塞的物体是否碰撞
     *
     * return 要碰撞的方向 如果为null说明没有碰撞
     */
    fun willCollision(blockabe: Blockabe):Direction?{
        //未来的坐标
        var x = this.x
        var y = this.y
        //将要碰撞时做判断
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        //和边界进行检测
        //越界判断
        if (x < 0) return Direction.LEFT
        if (x > Config.gameWidth - width) return Direction.RIGHT
        if (y < 0) return Direction.UP
        if (y > Config.gameHeight - height) return Direction.DOWN

        val collision = checkCollision(blockabe.x, blockabe.y, blockabe.width, blockabe.height
            , x, y, width, height)

        return if (collision) currentDirection else null
    }

    /**
     * 通知碰撞
     */
    fun notifyBlock(direction: Direction?,blockabe: Blockabe?)
}