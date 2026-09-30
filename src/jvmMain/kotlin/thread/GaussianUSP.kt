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
 *** name：USPThread
 *** date：2023/12/28  22:39
 *** filename：USPThread
 *** desc：USP
 ***/

/** USP速点宏线程：侧键触发的高频点射节奏，延迟高斯随机化 */
class GaussianUSP : Thread() {
    private var robot: Robot? = null

    private var leftMean = 50

    private var leftStdDev = 5

    private var rightMean = 60

    private var rightStdDev = 5

    var version = 0


    private var down = 0

    private var up = 0

    @Volatile var isStop = true

    fun stopMacro() {
        isStop = false
    }


    /** 区间端点换算高斯参数：均值取中点，标准差取区间/6 */
    fun setDelay(delay: Delay) {
        leftMean = (delay.start + delay.stop) / 2
        leftStdDev = ((delay.stop - leftMean) / 3).coerceAtLeast(1)
        rightMean = (delay.begin + delay.end) / 2
        rightStdDev = ((delay.end - rightMean) / 3).coerceAtLeast(1)
    }


    override fun run() {
        robot = Robot()
        val generator = RandomDataGenerator()
        if (version == 0) {
            while (isStop) {
                robot!!.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                down = generator.nextGaussian(leftMean.toDouble(), leftStdDev.toDouble()).toInt()
                robot!!.delay(down.coerceIn(1, 99999))
                println(down)
                robot!!.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
                up = generator.nextGaussian(rightMean.toDouble(), rightStdDev.toDouble()).toInt()
                robot!!.delay(up.coerceIn(1, 99999))
                println(up)
            }
        } else {
            while (isStop) {
                robot!!.keyPress(KeyEvent.VK_K)
                down = generator.nextGaussian(leftMean.toDouble(), leftStdDev.toDouble()).toInt()
                robot!!.delay(down.coerceIn(1, 99999))
                robot!!.keyRelease(KeyEvent.VK_K)
                up = generator.nextGaussian(rightMean.toDouble(), rightStdDev.toDouble()).toInt()
                robot!!.delay(up.coerceIn(1, 99999))
            }
        }

    }
}
