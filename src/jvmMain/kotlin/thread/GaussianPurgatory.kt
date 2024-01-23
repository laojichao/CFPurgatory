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

class GaussianPurgatory : Thread() {
    private var robot: Robot? = null

    private var leftMean = 30

    private var leftStdDev = 6

    private var rightMean = 150

    private var rightStdDev = 10

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
        println("lianyu")
        val generator = RandomDataGenerator()
        if (version == 0) {
            while (isStop) {
                robot!!.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                down = generator.nextGaussian(rightMean.toDouble(), rightStdDev.toDouble()).toInt()
                robot!!.delay(down)
//                println(down)
                robot!!.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
                up = generator.nextGaussian(leftMean.toDouble(), leftStdDev.toDouble()).toInt()
                robot!!.delay(up)
//                println(up)
            }
        } else {
            while (isStop) {
                robot!!.keyPress(KeyEvent.VK_K)
                down = generator.nextGaussian(rightMean.toDouble(), rightStdDev.toDouble()).toInt()
//                robot!!.delay(down)
                robot!!.keyRelease(KeyEvent.VK_K)
                up = generator.nextGaussian(leftMean.toDouble(), leftStdDev.toDouble()).toInt()
//                robot!!.delay(up)
            }
        }

    }
}