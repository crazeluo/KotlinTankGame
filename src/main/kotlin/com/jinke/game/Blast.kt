package com.jinke.game

import com.jinke.game.business.Distoryable
import com.jinke.game.model.Model
import org.itheima.kotlin.game.core.Painter

class Blast(override val x: Int, override val y: Int) :Distoryable {

    override var width: Int=Config.black
    override var height: Int=Config.black
    private var imageList:ArrayList<String> = arrayListOf<String>()
    private var index:Int = 1
    init {
        (1..32).forEach {
            imageList.add("img/blast_$it.png")
        }
    }
    override fun draw() {
        val i = index%imageList.size
        Painter.drawImage(imageList[i],x,y)
        index++
    }

    override fun isDistory(): Boolean {
        return index%imageList.size==0
    }
}