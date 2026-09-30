package ui

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter

/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：ui.TrayIcon
 *** date：2024/1/2  15:54
 *** filename：ui.TrayIcon
 *** desc：自定义Painter，还是直接用图片比较好
 ***/

object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFF34A500))
    }
}