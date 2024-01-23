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

class GaussianUSP : Thread() {
    private var robot: Robot? = null

    private var leftMean = 50

    private var leftStdDev = 5

    private var rightMean = 60

    private var rightStdDev = 5

    var version = 0


    private var down = 0

    private var up = 0

    var isStop = true

    fun stopMacro() {
        isStop = false
    }


    fun setDelay(delay: Delay) {
        leftMean = (delay.start + delay.stop) / 2
        leftStdDev = (delay.stop - leftMean) / 3
        rightMean = (delay.begin + delay.end) / 2
        rightStdDev = (delay.end - rightMean) / 3
    }


    override fun run() {
        robot = Robot()
        val generator = RandomDataGenerator()
        if (version == 0) {
            while (isStop) {
                robot!!.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                down = generator.nextGaussian(leftMean.toDouble(), leftStdDev.toDouble()).toInt()
                robot!!.delay(down)
                println(down)
                robot!!.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
                up = generator.nextGaussian(rightMean.toDouble(), rightStdDev.toDouble()).toInt()
                robot!!.delay(up)
                println(up)
            }
        } else {
            while (isStop) {
                robot!!.keyPress(KeyEvent.VK_K)
                down = generator.nextGaussian(leftMean.toDouble(), leftStdDev.toDouble()).toInt()
                robot!!.delay(down)
                robot!!.keyRelease(KeyEvent.VK_K)
                up = generator.nextGaussian(rightMean.toDouble(), rightStdDev.toDouble()).toInt()
                robot!!.delay(up)
            }
        }

    }
}