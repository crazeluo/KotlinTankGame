package com.jinke.game.business

import com.jinke.game.model.Model

/**
 * 被攻击的能力
 */
interface Sufferable:Model{
    /**
     * 生命值
     */
    var blood:Int

    fun notifySuffer(attack: Attackable):Array<Model>?
}