package com.jinke.game

import com.jinke.game.business.*
import com.jinke.game.enums.Direction
import com.jinke.game.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow:Window(title = "坦克大战",icon = "img/logo.jpg",width = Config.gameWidth,height = Config.gameHeight) {


    private var models= CopyOnWriteArrayList<Model>()
    private lateinit var myTank:MyTank;

    private var gameOver = false

    private var enemyToTalNum = 20
    private val enemtActivityNum = 6

    private var myNum = 3

    private var bornNum = 0
    //地方出生点
    private val enemyLocation = arrayListOf<Pair<Int,Int>>()

    private lateinit var myBornLocation : Pair<Int,Int>
    override fun onCreate() {

        Composer.play("snd/start.wav")
        //创建地图
        val file = File(javaClass.getResource("/map/1.map").path)
        val lines:List<String> = file.readLines()
        var lineNum = 0;


        lines.forEach {
            var columnNum = 0
            it.toCharArray().forEach {column->
                when(column){
                    '砖'-> models.add(Wall(Config.black*columnNum,Config.black*lineNum))
                    '草'-> models.add(Grass(Config.black*columnNum,Config.black*lineNum))
                    '水'-> models.add(Water(Config.black*columnNum,Config.black*lineNum))
                    '铁'-> models.add(Steel(Config.black*columnNum,Config.black*lineNum))
                    '我'-> {
                        myTank = MyTank(Config.black*columnNum,Config.black*lineNum)
                        myBornLocation = Pair(Config.black*columnNum,Config.black*lineNum)
                        myNum--
                        models.add(myTank)
                    }
                    '敌'->{
                        enemyLocation.add(Pair(Config.black*columnNum,Config.black*lineNum))
                    }
                }
                columnNum++
            }
            lineNum++
        }

        models.add(Camp(Config.gameWidth/2-64,Config.gameHeight-96))
    }

    override fun onDisplay() {
        //界面渲染
        //绘制图像

        //绘制一个墙砖
        models.forEach {
            it.draw()
        }

    }

    override fun onKeyPressed(event: KeyEvent) {
        if (!gameOver){
            when(event.code){
                KeyCode.W->myTank.move(Direction.UP)
                KeyCode.A->myTank.move(Direction.LEFT)
                KeyCode.D->myTank.move(Direction.RIGHT)
                KeyCode.S->myTank.move(Direction.DOWN)
                KeyCode.ENTER->{
                    models.add(myTank.shot())
                    Composer.play("snd/fire.wav")
                }
            }
        }

    }

    override fun onRefresh() {

        //检测销毁
        models.filter { it is Distoryable }.forEach {
            //判断具备销毁能力的物体，是否被销毁了
            if ((it as Distoryable).isDistory()) {
                models.remove(it)
                if (it is Enemy) enemyToTalNum--
                if (it is MyTank) myNum--

                val destroy = it.showDistory()
                destroy?.let {
                    models.addAll(destroy)
                }
            }
        }

        if (gameOver) return

        //移动物体和障碍物体判断
        models.filter { it is Moveable  }.forEach { move->
            move as Moveable
            var badDirection:Direction? = null
            var badBlock: Blockabe? = null
            models.filter { (it is Blockabe) and (move!=it) }.forEach blockTag@{ block->

                block as Blockabe

                val direction : Direction? = move.willCollision(block)

                direction?.let {
                    badDirection = direction
                    badBlock = block
                    return@blockTag
                }
            }


            move.notifyBlock(badDirection,badBlock)
        }
        models.filter { it is AutoMove }.forEach { autoMove->
            autoMove as AutoMove
            autoMove.autoMove()
        }

        models.filter { it is Distoryable }.forEach {

            if ((it as Distoryable).isDistory()){
                models.remove(it)
                val showDistory = it.showDistory()
                showDistory?.let {
                    models.addAll(showDistory)
                }
            }
        }

        //攻击和被攻击的是否碰撞
        models.filter { it is Attackable }.forEach { attack->
            attack as Attackable
            models.filter { (it is Sufferable) and (attack.owner!=it) and (attack!=it) }.forEach sufferTag@{suffer->
                suffer as Sufferable
                //是否碰撞
                if (attack.isCollision(suffer)){
                    //通知攻击者产生碰撞
                    attack.notifyAttack(suffer)
                    //通知被攻击者产生碰撞
                    val sufferModel:Array<Model>? = suffer.notifySuffer(attack)
                    suffer?.let {
                        sufferModel?.forEach {
                            models.add(it)
                        }
                    }
                    return@sufferTag
                }
            }

        }

        //检测自动射击
        models.filter { it is AutoShot }.forEach {
            val shot = (it as AutoShot).autoShot()
            shot?.let {
                models.add(shot)
            }
        }
        if ((models.filter { it is MyTank }.isEmpty())){
            myTank = MyTank(myBornLocation.first,myBornLocation.second)
            models.add(myTank)
        }
        //检测游戏是否结束
        if ((models.filter { it is Camp }.isEmpty()) or (enemyToTalNum<=0) or (myNum<0)){
            gameOver = true
        }


        //检测当前页面活动数量小于激活数量
        if ((enemyToTalNum>0) and (models.filter { it is Enemy }.size< enemtActivityNum)){
            val i = bornNum % enemyLocation.size
            val pair = enemyLocation[i]
            models.add(Enemy(pair.first,pair.second))
            bornNum++
        }

    }
}