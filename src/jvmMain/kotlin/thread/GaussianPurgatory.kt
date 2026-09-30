package thread

import bean.Delay
import org.apache.commons.math3.random.RandomDataGenerator
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent

/****
 *** author：lao
 *** package：
 *** project：CSGO
 *** name：Knife
 *** date：2023/12/28  22:39
 *** filename：Knife
 *** desc：炼狱
 ***/

/**
 * 炼狱连点宏线程：按住触发键期间循环点击，
 * 按下时长与点击间隔均由高斯分布随机化以模拟人手。
 */
class GaussianPurgatory : Thread() {
    private var robot: Robot? = null

    private var leftMean = 30

    private var leftStdDev = 6

    private var rightMean = 150

    private var rightStdDev = 10

    var version = 0

    private var down = 0

    private var up = 0

    @Volatile var isStop = true

    fun stopMacro() {
        isStop = false
    }


    /** 区间端点换算高斯参数：均值取中点，标准差取区间/6(3σ覆盖) */
    fun setDelay(delay: Delay) {
        leftMean = (delay.start + delay.stop) / 2
        leftStdDev = ((delay.stop - leftMean) / 3).coerceAtLeast(1)
        rightMean = (delay.begin + delay.end) / 2
        rightStdDev = ((delay.end - rightMean) / 3).coerceAtLeast(1)
    }

    override fun run() {
        robot = Robot()
        println("lianyu")
        val generator = RandomDataGenerator()
        // 版本0：鼠标左键连点（按下时长用begin~end，间隔用start~stop）
        if (version == 0) {
            while (isStop) {
                robot!!.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                down = generator.nextGaussian(rightMean.toDouble(), rightStdDev.toDouble()).toInt()
                robot!!.delay(down.coerceIn(1, 99999))
//                println(down)
                robot!!.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
                up = generator.nextGaussian(leftMean.toDouble(), leftStdDev.toDouble()).toInt()
                robot!!.delay(up.coerceIn(1, 99999))
//                println(up)
            }
        // 版本1：改发K键（攻击键映射为键盘K）
        } else {
            while (isStop) {
                robot!!.keyPress(KeyEvent.VK_K)
                down = generator.nextGaussian(rightMean.toDouble(), rightStdDev.toDouble()).toInt()
robot!!.delay(down.coerceIn(1, 99999))
                robot!!.keyRelease(KeyEvent.VK_K)
                up = generator.nextGaussian(leftMean.toDouble(), leftStdDev.toDouble()).toInt()
robot!!.delay(up.coerceIn(1, 99999))
            }
        }

    }
}
