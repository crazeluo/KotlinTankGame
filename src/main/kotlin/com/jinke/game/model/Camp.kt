package com.jinke.game.model

import com.jinke.game.Blast
import com.jinke.game.Config
import com.jinke.game.business.Attackable
import com.jinke.game.business.Blockabe
import com.jinke.game.business.Distoryable
import com.jinke.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

class Camp(override var x: Int, override var y: Int) :Model,Sufferable,Blockabe,Distoryable {

    override var blood: Int=10

    override fun notifySuffer(attack: Attackable): Array<Model>? {
        blood-=attack.attackPower
        if (blood == 3 || blood == 6) {
            val x = x - 32
            val y = y - 32
            return arrayOf(Blast(x, y)
                , Blast(x + 32, y)
                , Blast(x + Config.black, y)
                , Blast(x + Config.black + 32, y)
                , Blast(x + Config.black * 2, y)
                , Blast(x, y + 32)
                , Blast(x, y + Config.black)
                , Blast(x, y + Config.black + 32)
                , Blast(x + Config.black * 2, y + 32)
                , Blast(x + Config.black * 2, y + Config.black)
                , Blast(x + Config.black * 2, y + Config.black + 32))
        }
        return null
    }

    override var width: Int = Config.black*2
    override var height: Int = Config.black+32

    var imgResource = "img/steel_small.gif";

    override fun draw() {

        //血量低于 6个时画的时 砖墙
        //血量低于 3个时画的时 没有墙
        if (blood <= 3) {
            width = Config.black
            height = Config.black
            x = (Config.gameWidth - Config.black) / 2
            y = Config.gameHeight - Config.black
            Painter.drawImage("img/camp.gif", x, y)

        } else if (blood <= 6) {

            //绘制外围的砖块
            Painter.drawImage("img/wall_small.gif", x, y)
            Painter.drawImage("img/wall_small.gif", x + 32, y)
            Painter.drawImage("img/wall_small.gif", x + 64, y)
            Painter.drawImage("img/wall_small.gif", x + 96, y)

            Painter.drawImage("img/wall_small.gif", x, y + 32)
            Painter.drawImage("img/wall_small.gif", x, y + 64)

            Painter.drawImage("img/wall_small.gif", x + 96, y + 32)
            Painter.drawImage("img/wall_small.gif", x + 96, y + 64)

            Painter.drawImage("img/camp.gif", x + 32, y + 32)

        } else {

            //绘制外围的砖块
            Painter.drawImage("img/steel_small.gif", x, y)
            Painter.drawImage("img/steel_small.gif", x + 32, y)
            Painter.drawImage("img/steel_small.gif", x + 64, y)
            Painter.drawImage("img/steel_small.gif", x + 96, y)

            Painter.drawImage("img/steel_small.gif", x, y + 32)
            Painter.drawImage("img/steel_small.gif", x, y + 64)

            Painter.drawImage("img/steel_small.gif", x + 96, y + 32)
            Painter.drawImage("img/steel_small.gif", x + 96, y + 64)

            Painter.drawImage("img/camp.gif", x + 32, y + 32)

        }
    }

    override fun isDistory(): Boolean {
        return blood<=0
    }

    override fun showDistory(): Array<Model>? {

        return arrayOf(Blast(x - 32, y - 32) ,Blast(x, y - 32)
            , Blast(x + 32, y - 32)

            , Blast(x - 32, y)
            , Blast(x, y)
            , Blast(x + 32, y)

            , Blast(x - 32, y + 32)
            , Blast(x, y + 32)
            , Blast(x + 32, y + 32))
    }
}