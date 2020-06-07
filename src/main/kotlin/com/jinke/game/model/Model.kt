package com.jinke.game.model

import com.jinke.game.Config

interface Model {
    //坐标
    val x:Int
    val y:Int

    //宽高
    var width:Int
    var height:Int

    //显示
    fun draw()

    fun checkCollision(x1:Int,y1:Int,w1:Int,h1:Int,
                       x2:Int,y2:Int,w2:Int,h2:Int):Boolean{
        return when{
            y2+w2<=y1->false//阻挡物在运动物上方
            y1+h1<=y2->false
            x2+h2<=x1->false
            else -> x1+w1>x2
        }
    }
}