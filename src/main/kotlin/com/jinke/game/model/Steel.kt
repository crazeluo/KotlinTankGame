package com.jinke.game.model

import com.jinke.game.business.Blockabe
import com.jinke.game.Config
import com.jinke.game.business.Attackable
import com.jinke.game.business.Distoryable
import com.jinke.game.business.Sufferable
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

/**
 * 铁块
 */
class Steel(override var x: Int, override var y: Int) : Blockabe,Sufferable {


    override var blood: Int=6

    override fun notifySuffer(attack: Attackable): Array<Model>? {
        Composer.play("snd/hit.wav")
        return null
    }

    override var width: Int=Config.black
    override var height: Int=Config.black

    override fun draw() {
        Painter.drawImage("img/steel.gif",x,y)
    }

}