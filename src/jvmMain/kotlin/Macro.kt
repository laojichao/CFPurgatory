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

/**
 * 宏引擎：注册全局鼠标/键盘钩子，监听按键并驱动各宏线程。
 * 按下时按版本/侧键配置启动对应线程，松开时停止。
 */
class Macro : NativeMouseInputListener, NativeKeyListener {

    @Volatile var enable = false // 宏总开关(鼠标中键切换)
    @Volatile var timeEnable = false // 授权有效开关,过期禁止开启

    @Volatile var version = 0 // 版本模式:0=侧键炼狱 1=K键炼狱
    @Volatile var select = 0 // 侧键功能:0=USP速点 1=快刀

    @Volatile var pDelay = Delay() // 炼狱延迟配置
    @Volatile var uDelay = Delay() // USP延迟配置

    private var pThread: GaussianPurgatory? = null // 炼狱宏线程
    private var uspThread: GaussianUSP? = null // USP宏线程
    private var knifeThread: KnifeThread? = null // 快刀宏线程
    var listener : MouseListener? = null // 开关状态回调(刷新UI)


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

    /** 按下：按配置启动对应宏线程；中键用于切换总开关 */
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
                println("侧键")
                pThread = GaussianPurgatory()
                pThread?.setDelay(pDelay)
                pThread?.version = version
                pThread?.start()
            }
        }

        if (nativeEvent?.button == NativeMouseEvent.BUTTON3 && timeEnable) {
            enable = !enable
            listener?.mouseEnable(enable)
            if (enable) {
                println("开关已经打开")
            } else {
                println("开关已经关闭")
            }
        }
    }

    /** 松开：停止对应宏线程 */
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
