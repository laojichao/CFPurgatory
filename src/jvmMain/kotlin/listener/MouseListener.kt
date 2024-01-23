package listener

import jdk.jfr.Enabled

/****
 *** author：lao
 *** package：
 *** project：CSGO
 *** name：listener.MouseListener
 *** date：2023/12/29  16:10
 *** filename：listener.MouseListener
 *** desc：监听回调
 ***/

interface MouseListener {
    fun mouseEnable(enabled : Boolean)
}