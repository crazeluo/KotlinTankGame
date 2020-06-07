import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window

/**
 * 窗口，继承游戏引擎里的窗口
 */

class MyWindow:Window(){
    override fun onCreate() {
        //窗体创建

    }

    override fun onDisplay() {
        //窗体渲染，不停执行

        Painter.drawImage("tank_u.gif",0,0)
    }

    override fun onKeyPressed(event: KeyEvent) {
        //按键响应
        when(event.code){
            KeyCode.ENTER-> println("点击了enter键")
            KeyCode.A->Composer.play("blast.wav")
        }
    }

    override fun onRefresh() {
        //刷新，做耗时操作
    }
}

fun main(args:Array<String>){
    Application.launch(MyWindow::class.java)
}