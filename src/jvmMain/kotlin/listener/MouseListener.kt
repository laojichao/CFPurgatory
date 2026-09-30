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
    /** 宏总开关状态变化时回调（用于刷新界面状态文案） */
    fun mouseEnable(enabled : Boolean)
}
