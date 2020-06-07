package com.jinke.game.ext

import com.jinke.game.model.Model

fun Model.checkCollision(model: Model):Boolean{

    return checkCollision(x,y,width,height,model.x,model.y,model.width,model.height)
}