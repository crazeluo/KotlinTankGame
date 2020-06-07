package com.jinke.game.business

import com.jinke.game.enums.Direction
import com.jinke.game.model.Model

/**
 * 攻击的能力
 */
interface Attackable {
    //攻击所有者
    val owner:Model

    val attackPower:Int

    fun isCollision(suffer: Sufferable):Boolean

    fun notifyAttack(suffer: Sufferable)
}