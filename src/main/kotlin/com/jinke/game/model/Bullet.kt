package com.jinke.game.model

import com.jinke.game.Blast
import com.jinke.game.Config
import com.jinke.game.business.Attackable
import com.jinke.game.business.AutoMove
import com.jinke.game.business.Distoryable
import com.jinke.game.business.Sufferable
import com.jinke.game.enums.Direction
import com.jinke.game.ext.checkCollision
import org.itheima.kotlin.game.core.Painter

/**
 * 子彈
 */
class Bullet(override val owner: Model,override val currentDirection: Direction, creat:(width:Int,height:Int)->Pair<Int,Int>
             ) :AutoMove,Distoryable,Attackable,Sufferable{
    override val speed: Int=20

    private var imageSource:String = when(currentDirection){
        Direction.UP->"img/bullet_u.gif"
        Direction.DOWN->"img/bullet_d.gif"
        Direction.LEFT->"img/bullet_l.gif"
        Direction.RIGHT->"img/bullet_r.gif"
    }
    override var blood: Int=1

    override fun notifySuffer(attack: Attackable): Array<Model>? {
        return arrayOf(Blast(x,y))
    }

    override var x: Int = 0
    override var y: Int = 0

    var isDistoryed = false

    override var width: Int = 0
    override var height: Int = 0

    init {
        val size = Painter.size(imageSource)
        width = size[0]
        height = size[1]
        val pair = creat(width,height)
        x = pair.first
        y = pair.second
    }
    override fun draw() {

        Painter.drawImage(imageSource,x,y)
    }
    override fun autoMove() {
       when(currentDirection){
           Direction.UP -> y-=speed
           Direction.DOWN->y+=speed
           Direction.LEFT->x-=speed
           Direction.RIGHT->x+=speed
       }
    }
    override fun isDistory(): Boolean {
        if (isDistoryed){
            return true
        }
        val isDestory:Boolean = when{
            x<-width->true
            x>Config.gameWidth->true
            y<-height->true
            y>Config.gameHeight->true
            else ->false
        }
        return isDestory
    }

    override val attackPower: Int=1

    override fun isCollision(suffer: Sufferable): Boolean {

        return checkCollision(suffer)
    }

    override fun notifyAttack(suffer: Sufferable) {
//        println("子弹接受到碰撞。。")
        //子弹销毁
        isDistoryed=true

    }

}