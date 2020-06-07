package com.jinke.game.model

import com.jinke.game.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 草坪
 */
class Grass(override var x: Int, override var y: Int) :Model{

    override var width: Int=Config.black
    override var height: Int=Config.black

    override fun draw() {
        Painter.drawImage("img/grass.gif",x,y)
    }

}