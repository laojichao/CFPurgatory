import bean.Delay
import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener
import listener.MouseListener
import thread.GaussianPurgatory
import thread.GaussianUSP
import thread.KnifeThread


/****
 *** author：lao
 *** package：
 *** project：CSGO
 *** name：Macro
 *** date：2023/12/28  21:39
 *** filename：Macro
 *** desc：宏脚本
 ***/

class Macro : NativeMouseInputListener, NativeKeyListener {

    var enable = false
    var version = 0
    var select = 0

    var pDelay = Delay()
    var uDelay = Delay()

    private var pThread: GaussianPurgatory? = null
    private var uspThread: GaussianUSP? = null
    private var knifeThread: KnifeThread? = null
    var listener : MouseListener? = null


    constructor(version: Int, select: Int, pDelay: Delay, uDelay: Delay){
        println("Macro")
        this.version = version
        this.select = select
        this.pDelay = pDelay
        this.uDelay = uDelay
        GlobalScreen.addNativeMouseMotionListener(this)
        //鼠标点击监听器
        GlobalScreen.addNativeMouseListener(this)
        //按键监听器
        GlobalScreen.addNativeKeyListener(this)

    }


    override fun nativeMouseClicked(nativeEvent: NativeMouseEvent?) {
//        println("nativeMouseClicked")//这个函数有延迟
    }

    override fun nativeMousePressed(nativeEvent: NativeMouseEvent?) {
        if (enable) {
            if (version == 1 && nativeEvent?.button == NativeMouseEvent.BUTTON1) {
                pThread = GaussianPurgatory()
                pThread?.setDelay(pDelay)
                pThread?.version = version
                pThread?.start()
            } else if (nativeEvent?.button == NativeMouseEvent.BUTTON4) {
                println("侧键4")
                if (select == 0) {
                    println("USP")
                    uspThread = GaussianUSP()
                    uspThread?.setDelay(uDelay)
                    uspThread?.version = version
                    uspThread?.start()
                } else if (select == 1) {
                    println("快刀")
                    knifeThread = KnifeThread()
                    knifeThread?.version = version
                    knifeThread?.start()
                }
            } else if (version == 0 && nativeEvent?.button == NativeMouseEvent.BUTTON5) {
                println("侧键5")
                pThread = GaussianPurgatory()
                pThread?.setDelay(pDelay)
                pThread?.version = version
                pThread?.start()
            }
        }

        if (nativeEvent?.button == NativeMouseEvent.BUTTON3) {
            enable = !enable
            listener?.mouseEnable(enable)
            if (enable) {
                println("开关已经打开")
            } else {
                println("开关已经关闭")
            }
        }
    }

    override fun nativeMouseReleased(nativeEvent: NativeMouseEvent?) {
        if (version == 1 && nativeEvent?.button == NativeMouseEvent.BUTTON1) {
            pThread?.stopMacro()
            pThread = null
        } else if (nativeEvent?.button == NativeMouseEvent.BUTTON4) {
            if (select == 0) {
                uspThread?.stopMacro()
                uspThread = null
            } else if (select == 1) {
                knifeThread?.stopMacro()
                knifeThread = null
            }
        } else if (version == 0 && nativeEvent?.button == NativeMouseEvent.BUTTON5) {
            pThread?.stopMacro()
            pThread = null
        }
    }

    override fun nativeMouseMoved(nativeEvent: NativeMouseEvent?) {
        super.nativeMouseMoved(nativeEvent)
    }

    override fun nativeMouseDragged(nativeEvent: NativeMouseEvent?) {
        super.nativeMouseDragged(nativeEvent)
    }

    override fun nativeKeyTyped(nativeEvent: NativeKeyEvent?) {
        super.nativeKeyTyped(nativeEvent)
    }

    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent?) {
        super.nativeKeyPressed(nativeEvent)
    }

    override fun nativeKeyReleased(nativeEvent: NativeKeyEvent?) {
        super.nativeKeyReleased(nativeEvent)
    }
}