package thread

import org.apache.commons.math3.random.RandomDataGenerator
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent

/****
 *** author：lao
 *** package：
 *** project：CSGO
 *** name：thread.KnifeThread
 *** date：2023/12/28  22:39
 *** filename：thread.KnifeThread
 *** desc：炼狱快刀
 ***/

class KnifeThread : Thread() {
    private var robot: Robot? = null


    var version = 0


    private var delay = 100

    var isStop = true

    fun stopMacro() {
        isStop = false
    }

    override fun run() {
        robot = Robot()
        val generator = RandomDataGenerator()
        if (version == 0) {
            while (isStop) {
                delay = generator.nextInt(9, 11)
                robot!!.mousePress(InputEvent.BUTTON3_DOWN_MASK)
                robot!!.delay(delay)
                delay = generator.nextInt(279, 281)
                robot!!.mouseRelease(InputEvent.BUTTON3_DOWN_MASK)
                robot!!.delay(delay)

                delay = generator.nextInt(9, 11)
                robot!!.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                robot!!.delay(delay)
                delay = generator.nextInt(9, 11)
                robot!!.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
                robot!!.delay(delay)
            }
        } else {
            while (isStop) {
                delay = generator.nextInt(9, 11)
                robot!!.mousePress(InputEvent.BUTTON3_DOWN_MASK)
                robot!!.delay(delay)
                delay = generator.nextInt(279, 281)
                robot!!.mouseRelease(InputEvent.BUTTON3_DOWN_MASK)
                robot!!.delay(delay)

                delay = generator.nextInt(9, 11)
                robot!!.keyPress(KeyEvent.VK_K)
                robot!!.delay(delay)
                delay = generator.nextInt(9, 11)
                robot!!.keyRelease(KeyEvent.VK_K)
                robot!!.delay(delay)
            }
        }

    }
}