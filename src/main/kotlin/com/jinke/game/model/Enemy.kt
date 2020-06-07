package com.jinke.game.model

import com.jinke.game.Blast
import com.jinke.game.Config
import com.jinke.game.business.*
import com.jinke.game.enums.Direction
import org.itheima.kotlin.game.core.Painter
import kotlin.random.Random

/**
 * 敌方坦克
 * 自动移动
 */
class Enemy(override var x: Int, override var y: Int) :Moveable,AutoMove,Blockabe,AutoShot,Sufferable,Distoryable{
    override var blood: Int= 5

    override fun notifySuffer(attack: Attackable): Array<Model>? {
        if (attack.owner is Enemy){
            return null
        }
        blood-=attack.attackPower

        return arrayOf(Blast(x,y))
    }

    override var width: Int= Config.black
    override var height: Int= Config.black
    override val speed: Int=8

    var lastShotTime = 0L
    var shotTime = 500

    var lastMoveTime = 0L
    var moveTime = 50
    override var currentDirection: Direction = randowDirection()
    //碰撞的方向
    var badDirection : Direction? = null
    //碰撞的障碍物
    var badBlack : Blockabe? = null
    override fun notifyBlock(direction: Direction?, blockabe: Blockabe?) {
        this.badDirection = direction
        this.badBlack = blockabe
    }

    override fun isDistory(): Boolean {
        return blood<=0
    }
    override fun draw() {
        val img = when(currentDirection){
            Direction.UP-> "img/enemy_1_u.gif"
            Direction.LEFT-> "img/enemy_1_l.gif"
            Direction.RIGHT-> "img/enemy_1_r.gif"
            Direction.DOWN-> "img/enemy_1_d.gif"
        }
        Painter.drawImage(img,x,y)
    }
    override fun autoMove() {
        var currentTime = System.currentTimeMillis()
        if (currentTime-lastMoveTime<moveTime){
            return
        }
        lastMoveTime = currentTime

        if (currentDirection==badDirection){
            currentDirection = randowDirection()
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

    private fun randowDirection():Direction{
        val index = Random.nextInt(4)
        val direction =when(index){
            0->Direction.DOWN
            1->Direction.UP
            2->Direction.RIGHT
            else->Direction.LEFT
        }
        if (direction==badDirection){
            randowDirection()
        }
        return direction
    }


    //自动射击
    override fun autoShot(): Model? {
        var currentTime = System.currentTimeMillis()
        if (currentTime-lastShotTime<shotTime){
            return null
        }
        lastShotTime = currentTime

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