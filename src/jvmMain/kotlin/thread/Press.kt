package thread

import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.util.*
import kotlin.random.Random

/****
 *** author：lao
 *** package：
 *** project：CSGO
 *** name：thread.Press
 *** date：2023/12/28  22:23
 *** filename：thread.Press
 *** desc：压枪
 ***/

class Press : Thread() {

    private var robot: Robot? = null

    private var leftMean = 30

    private var leftStdDev = 6

    private var rightMean = 150

    private var rightStdDev = 10

    var type = 0


    private var down = 0

    private var up = 0

    var isStop = true

    fun stopMacro() {
        isStop = false
    }


    fun setParameters(pStart: Int, pEnd: Int, rStart: Int, rEnd: Int) {
        leftMean = (pStart + pEnd) / 2
        leftStdDev = (pEnd - leftMean) / 3
        rightMean = (rStart + rEnd) / 2
        rightStdDev = (rEnd - rightMean) / 3
    }

    override fun run() {
        robot = Robot()

        if (type == 0) {
            while (isStop) {
                robot!!.mousePress(InputEvent.BUTTON1_DOWN_MASK)
                down = Random.nextInt(100, 180)
                robot!!.delay(down)
                robot!!.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
                up = Random.nextInt(10, 50)
                robot!!.delay(up)
                println(up)
            }
        } else {
            while (isStop) {
                robot!!.keyPress(KeyEvent.VK_K)
                down = Random.nextInt(100, 180)
                robot!!.delay(down)
                robot!!.keyRelease(KeyEvent.VK_K)
                up = Random.nextInt(10, 50)
                robot!!.delay(up)
            }
        }

    }

}