package com.jinke.game.model

import com.jinke.game.Blast
import com.jinke.game.business.Blockabe
import com.jinke.game.Config
import com.jinke.game.business.Attackable
import com.jinke.game.business.Distoryable
import com.jinke.game.business.Sufferable
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

/**
 * 墙砖
 */
class Wall(override var x: Int, override var y: Int) : Blockabe,Sufferable,Distoryable {

    override var width: Int=Config.black
    override var height: Int=Config.black

    override fun draw() {
        Painter.drawImage("img/wall.gif",x,y)
    }

    override var blood: Int=3

    override fun notifySuffer(attack: Attackable): Array<Model>? {
//        println("我被攻击了。。")
        blood-=attack.attackPower

        Composer.play("snd/hit.wav")

        return arrayOf(Blast(x,y))
    }
    override fun isDistory(): Boolean {
        return blood<=0
    }

//    //坐标
//    var x:Int = 100
//    var y:Int = 100
//    //大小
//    var width:Int = Config.black
//    var height:Int = Config.black
//    //显示
//
//    fun draw(){
//        Painter.drawImage("img/wall.gif",x,y)
//    }

}