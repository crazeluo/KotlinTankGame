package com.jinke.game.model

import com.jinke.game.Blast
import com.jinke.game.Config
import com.jinke.game.business.*
import com.jinke.game.enums.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 我方坦克
 * 移动能力
 * 阻挡能力
 */
class MyTank(override var x: Int, override var y: Int) : Moveable,Blockabe,Sufferable,Distoryable {


    override var blood: Int=3

    override fun notifySuffer(attack: Attackable): Array<Model>?  {
        if (attack.owner is MyTank){
            return null
        }
        blood-=attack.attackPower

        return arrayOf(Blast(x,y))
    }
    override fun isDistory(): Boolean {
        return blood<=0
    }
    override var width: Int=Config.black
    override var height: Int = Config.black
    var isBlock = false

    public override var currentDirection:Direction = Direction.UP
    //移动速度
    override var speed = 8
    //碰撞的方向
    var badDirection : Direction? = null
    //碰撞的障碍物
    var badBlack : Blockabe? = null

    override fun draw() {
        when(currentDirection){
            Direction.UP->Painter.drawImage("img/tank_u.gif",x,y)
            Direction.LEFT->Painter.drawImage("img/tank_l.gif",x,y)
            Direction.RIGHT->Painter.drawImage("img/tank_r.gif",x,y)
            Direction.DOWN->Painter.drawImage("img/tank_d.gif",x,y)
        }

    }


    fun move(direction: Direction){
        if (this.currentDirection!=direction){
            this.currentDirection = direction
            return
        }
        if (this.currentDirection==badDirection){
            return
        }
        when(currentDirection){
            Direction.UP->y-=speed
            Direction.DOWN->y+=speed
            Direction.LEFT->x-=speed
            Direction.RIGHT->x+=speed

        }

        if (x<0)x=0
        if (x+width>Config.gameWidth)x=Config.gameWidth-width
        if (y<0)y=0
        if (y+height>Config.gameHeight)y = Config.gameHeight-height

    }


    override fun notifyBlock(direction: Direction?, blockabe: Blockabe?) {
        this.badDirection = direction
        this.badBlack = blockabe
    }
    //發射子彈
    fun shot():Bullet{


        return Bullet(this,currentDirection) { bulletWith, bulletHeight->
            //计算子弹的真实坐标
            var x:Int
            var y:Int

            when(currentDirection){
                Direction.UP->{
                    x = this.x+(width-bulletWith)/2
                    y= this.y - bulletHeight/2
                }
                Direction.DOWN->{
                    x = this.x+(width-bulletWith)/2
                    y = this.y + height-bulletHeight/2
                }

                Direction.LEFT->{
                    x = this.x - bulletWith/2
                    y = this.y + (height-bulletHeight)/2
                }
                Direction.RIGHT->{
                    x = this.x + width - bulletWith/2
                    y = this.y + (height-bulletHeight)/2
                }
            }
            Pair(x,y)
        }
    }
}