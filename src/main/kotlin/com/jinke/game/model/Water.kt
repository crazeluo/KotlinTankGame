package com.jinke.game.model

import com.jinke.game.business.Blockabe
import com.jinke.game.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 草坪
 */
class Water(override var x: Int, override var y: Int) : Blockabe {

    override var width: Int=Config.black
    override var height: Int=Config.black

    override fun draw() {
        Painter.drawImage("img/water.gif",x,y)
    }

}