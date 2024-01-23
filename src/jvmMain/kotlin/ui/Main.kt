package ui

import Macro
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import bean.Config
import bean.Delay
import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService
import listener.MouseListener
import org.apache.logging.log4j.LogManager
import utils.TextUtils
import widget.EditText
import java.awt.Desktop
import java.io.IOException
import java.net.URI
import javax.swing.UIManager

var title = "行囊助手"
var text = "鼠标滚轮按下开启或关闭"
var tips = "侧键炼狱和USP(左键炼狱)"

var warming = "侧键和左键炼狱不共存，只生效一个"
var macro : Macro? = null
var config : Config?= null
val fileName = "config.json"
val textStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.2.sp
)

val logger = LogManager.getLogger("ui/Main.kt.kt")

@Composable
@Preview
fun App() {
    var status by remember { mutableStateOf("开关已经关闭") }
    var warning ="开源版本,后续不再更新"
    var editEnable by remember { mutableStateOf(true) }

    val listener = object : MouseListener {
        override fun mouseEnable(enabled: Boolean) {
            if (enabled) {
                status = "开关已经打开"
            } else {
                status = "开关已经关闭"
            }
        }
    }
    macro!!.listener = listener
    //主页面
    MaterialTheme {
        //主面板 //全局纵向布局
        ResizeWidthColumn(Modifier.fillMaxSize().background(
            brush = Brush.linearGradient(colors = listOf(
                Color(0x551abc9c),
                Color(0x553498db),
                Color(0x559b59b6),
                Color(0x558e44ad),
                Color(0x5527ae60),
                Color(0x552980b9),

                ))),
            true) {

            //状态显示区
            Row(
                modifier = Modifier.fillMaxSize().padding(10.dp, 5.dp, 10.dp, 5.dp).
                background(brush = Brush.verticalGradient(colors = listOf(
                    Color(0x551abc9c),
                    Color(0x553498db),
                    Color(0x5527ae60),
                    Color(0x552980b9),
                    Color(0x559b59b6),
                    Color(0x558e44ad),
                )),
                    shape = RoundedCornerShape(10.dp)),

                ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(10.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text, style = textStyle)
                    Text(tips, style = textStyle)
                    Text(status, style = textStyle, color = Color.Red)
                    Text(warning, style = textStyle)
                    Text(warming, style = textStyle)
                }

            }
            //功能选择区
            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp, 5.dp, 10.dp, 5.dp).
                background(brush = Brush.linearGradient(colors = listOf(
                    Color(0x55fc5c65),
                    Color(0x55fd9644),
                    Color(0x5545aaf2),
                    Color(0x553867d6),
                    Color(0x55778ca3),
                    Color(0x550fb9b1),
                )),
                    shape = RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(10.dp, 5.dp, 10.dp, 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "版本选择：", style = textStyle)
                    val items = listOf(
                        "侧键炼狱(侧键前进键) ",
                        "左键炼狱改攻击键为K ",
                    )
                    dropDownMenu(items = items, onSelected = {
                        config!!.version = it
                        macro!!.version = it
                        TextUtils.writeConfig(fileName, config!!)
                    })
                }

                Row(
                    modifier = Modifier.fillMaxSize().padding(10.dp, 5.dp, 10.dp, 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text ="侧键功能：", style = textStyle)
                    val items = listOf(
                        "USP速点(侧键后退键) ",
                        "炼狱快刀(侧键后退键) ",
                    )
                    dropDownMenu(items = items, onSelected = {
                        logger.info(it)
                        config!!.xbutton = it
                        macro!!.select = it
                        TextUtils.writeConfig(fileName, config!!)
                    })
                }

            }

            //自定义输入区
            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp, 5.dp, 10.dp, 0.dp).
                background(brush = Brush.horizontalGradient(colors = listOf(
                    Color(0x5500b894),
                    Color(0x5581ecec),
                    Color(0x5574b9ff),
                    Color(0x556c5ce7),
                    Color(0x55fab1a0),
                    Color(0x55ff7675),
                )),
                    shape = RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var start by remember { mutableStateOf(config!!.pStart.toString()) }
                    var stop by remember { mutableStateOf(config!!.pStop.toString()) }
                    var begin by remember { mutableStateOf(config!!.pBegin.toString()) }
                    var end by remember { mutableStateOf(config!!.pEnd.toString()) }
                    Text(text = "炼狱延迟：", style = textStyle)
                    EditText(
                        value = start,
//                        textStyle = TextStyle(color = Color.Red, fontSize = 14.sp),
                        onValueChange = {value ->
                            start = value.filter { it.isDigit() }
                            if (start.length in 2..4) {
                                logger.info(start)
                                config!!.pStart = start.toInt()
                                macro!!.pDelay.start = start.toInt()
                                TextUtils.writeConfig(fileName, config!!)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.width(40.dp).height(30.dp),
                    )
                    Text(text =" ~ ", style = textStyle)
                    EditText(
                        value = stop,
                        onValueChange = { value ->
                            stop = value.filter { it.isDigit() }
                            if (stop.length in 2..4) {
                                config!!.pStop = stop.toInt()
                                macro!!.pDelay.stop = stop.toInt()
                                TextUtils.writeConfig(fileName, config!!)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.width(40.dp).height(30.dp),
                    )

                    Spacer(Modifier.width(10.dp).height(10.dp).size(10.dp))

                    EditText(
                        value = begin,
                        onValueChange = { value ->
                            begin = value.filter { it.isDigit() }
                            if (begin.length in 2..4) {
                                config!!.pBegin = begin.toInt()
                                macro!!.pDelay.begin = begin.toInt()
                                TextUtils.writeConfig(fileName, config!!)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.width(40.dp).height(30.dp),
                    )
                    Text(text = " ~ ", style = textStyle)
                    EditText(
                        value = end,
                        onValueChange = { value ->
                            end = value.filter { it.isDigit() }
                            if (end.length in 2..4) {
                                config!!.pEnd = end.toInt()
                                macro!!.pDelay.end = end.toInt()
                                TextUtils.writeConfig(fileName, config!!)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.width(40.dp).height(30.dp),
                    )
                }

                Row(
                    modifier = Modifier.fillMaxSize().padding(10.dp, 0.dp, 10.dp, 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "USP延迟：", style = textStyle)
                    var start by remember { mutableStateOf(config!!.uStart.toString()) }
                    var stop by remember { mutableStateOf(config!!.uStop.toString()) }
                    var begin by remember { mutableStateOf(config!!.uBegin.toString()) }
                    var end by remember { mutableStateOf(config!!.uEnd.toString()) }
                    EditText(
                        value = start,
                        onValueChange = { value ->
                            start = value.filter { it.isDigit() }
                            if (start.length in 2..4) {
                                config!!.uStart = start.toInt()
                                macro!!.uDelay.start = start.toInt()
                                TextUtils.writeConfig(fileName, config!!)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.width(40.dp).height(30.dp),
                    )
                    Text(text = " ~ ", style = textStyle)
                    EditText(
                        value = stop,
                        onValueChange = { value ->
                            stop = value.filter { it.isDigit() }
                            if (stop.length in 2..4) {
                                config!!.uStop = stop.toInt()
                                macro!!.uDelay.stop = stop.toInt()
                                TextUtils.writeConfig(fileName, config!!)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.width(40.dp).height(30.dp),
                    )

                    Spacer(Modifier.width(10.dp).height(10.dp).size(10.dp))

                    EditText(
                        value = begin,
                        onValueChange = { value ->
                            begin = value.filter { it.isDigit() }
                            if (begin.length in 2..4) {
                                config!!.uBegin = begin.toInt()
                                macro!!.uDelay.begin = begin.toInt()
                                TextUtils.writeConfig(fileName, config!!)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.width(40.dp).height(30.dp),
                    )
                    Text(text = " ~ ", style = textStyle)
                    EditText(
                        value = end,
                        onValueChange = { value ->
                            end = value.filter { it.isDigit() }
                            if (end.length in 2..4) {
                                config!!.uEnd = end.toInt()
                                macro!!.uDelay.end = end.toInt()
                                TextUtils.writeConfig(fileName, config!!)

                            }
                        },
                        singleLine = true,
                        modifier = Modifier.width(40.dp).height(30.dp),
                    )
                }
            }



        }

    }
}


// Start application
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
fun main() = application {

    //flatlaf是一个swing主题，可以通过在gradle中添加 implementation("com.formdev:flatlaf:2.3")使用
//     FlatIntelliJLaf.setup()
    config = TextUtils.readConfig(fileName)
    val pDelay = Delay(config!!.pStart, config!!.pStop, config!!.pBegin, config!!.pEnd)
    val uDelay = Delay(config!!.uStart, config!!.uStop, config!!.uBegin, config!!.uEnd)
    macro = Macro(config!!.version, config!!.xbutton, pDelay, uDelay)
    UIManager.put("TextComponent.arc", 5)
    UIManager.put("Component.focusWidth", 1)
    UIManager.put("Component.innerFocusWidth", 1)
    UIManager.put("Button.innerFocusWidth", 1)
    UIManager.put("TitlePane.unifiedBackground", true)
    UIManager.put("TitlePane.menuBarEmbedded", true)
    // 设置字体，设置字体抗锯齿
    System.setProperty("awt.useSystemAAFontSettings", "on")
    System.setProperty("swing.aatext", "true")

    // 将事件调度程序设置为swing-safe执行器服务。
    GlobalScreen.setEventDispatcher(SwingDispatchService())
    GlobalScreen.registerNativeHook()

    //菜单里面有一个exit功能，这个变量判断是否关闭应用
    //是否退出
    var isOpen by remember { mutableStateOf(true) }
    //界面是否可见
    var isMainWindowVisible by remember { mutableStateOf(true) }
    // 显示托盘图标
    var isShowTray by remember { mutableStateOf(true) }
    // 窗口状态
    var windowState = WindowState(
        isMinimized = false,
        size = DpSize(330.dp, 400.dp),
        position = WindowPosition.Aligned(Alignment.Center)
    )
    //托盘状态
    val trayState = rememberTrayState()
    //通知
    val notification = rememberNotification("助手通知", "消息来自炼狱助手!")

    var autoState by remember { mutableStateOf(config!!.auto) }

    val icon = painterResource("macro.svg")

    if (isOpen) {
        //onCloseRequest 一般写成这样 onCloseRequest = ::exitApplication
        //::exitApplications是系统调用，这里我们定义一个isOpen手动控制
        Window(
            onCloseRequest = {
                //点击退出按钮的反应
//                isOpen = false
                windowState.isMinimized = true
//                isShowTray = true
                isMainWindowVisible = false
            },
            icon = icon,
            title = title,
            visible = isMainWindowVisible,
            resizable = false,
            alwaysOnTop = false,
            state = windowState,
        ) {
            MenuBar {
                //mnemonic表示菜单快捷键，指定后按alt+对应的键是可以快速弹出菜单的
                Menu(text = "使用教程", mnemonic = 'F') {
                    Item(
                        text = "文档教程",
                        onClick = {
                            val url = "https://docs.qq.com/doc/DUG5mbElJd29Ud3hJ"
                            openUrl(url)
                        },
                        //显示快捷键
                        shortcut = KeyShortcut(Key.D, ctrl = true, alt = true),
                        icon = painterResource("pdf.svg")
                    )
                    Item(
                        text = "视频教程",
                        onClick = {
                            val url = "https://www.bilibili.com/video/BV1oQ4y177Xf/?share_source=copy_web&vd_source=aa575c65be8a88e034c089ec7e9f9e66"
                            openUrl(url)
                        },
                        shortcut = KeyShortcut(Key.V, ctrl = true, alt = true),
                        icon = painterResource("bilibili.svg")
                    )
                }

                Menu(text = "关于软件", mnemonic = 'A') {
                    //CheckboxItem可以做判断隐藏显示一些功能
                    CheckboxItem(
                        text = "开机自启",
                        checked = autoState,
                        onCheckedChange = {
                            autoState = it
                            config!!.auto = it
                            TextUtils.writeConfig(fileName, config!!)
                            AutoRun(it)
                        },
                        icon = painterResource("auto.svg")
                    )
                    Separator()
                    //菜单分割线
                    Separator()
                    //TrayIcon是一个自定义Painter，还是直接用图片比较好
                    Menu(text = "开源地址") {
                        Item(
                            text="提取码:Aut8",
                            icon = painterResource("cloud.svg")
                            , onClick = {
                                openUrl("https://www.123pan.com/s/y2B0Vv-AsUW3.html")
                            },
                            shortcut = KeyShortcut(Key.D, ctrl = true, alt = true)
                        )
                        Item(
                            text="github",
                            icon = painterResource("github.svg")
                            , onClick = {
                                openUrl("http://github.com/laojichao/CFPurgatory")
                            },
                            shortcut = KeyShortcut(Key.H, ctrl = true, alt = true)
                        )
                    }

                    Item(
                        text="退出",
                        icon = painterResource("exit.svg"),
                        onClick = { isOpen = false },
                        shortcut = KeyShortcut(Key.Escape),
                        mnemonic = 'E')
                }

                Menu(text = "温馨提示", mnemonic = 'A') {
                    Menu(text = "使用鼠标宏") {}
                    Separator()
                    Menu(text = "也可能十年"){}
                    Separator()
                    Menu(text = "请慎重使用") {}
                }
            }
            App()

        }

        //系统托盘
        if (isShowTray) {
            Tray(
                state = trayState,
                icon = icon,
                onAction = {
                    //双击打开界面
//                    isVisible = true
                    logger.info("onAction")
//                    isShowTray = false
                    windowState.isMinimized = false
                    isMainWindowVisible = true

                },
                menu = {
                    Item(
                        "显示" + title,
                        onClick = {
                            windowState.isMinimized = false
                            isMainWindowVisible = true
                        }
                    )
                    Item(
                        "发送系统通知",
                        onClick = {
                            trayState.sendNotification(notification)
                        }
                    )
                    Item(
                        "退出" + title,
                        onClick = {
                            isOpen = false
                        }
                    )
                }
            )
//            isMainWindowVisible = false
        }

    }
}



//打开浏览器
fun openUrl(url: String) {
    val desktop = Desktop.getDesktop()
    try {
        desktop.browse(URI(url))
    } catch (e: IOException) {
        e.printStackTrace()
    }
}


/**
 * 虚线边框
 * @param width 虚线宽度
 * @param radius 边框角度
 * @param color 边框颜色
 * 虚实间隔也是写死的10f
 **/
internal fun Modifier.dashedBorder(width: Dp, radius: Dp, color: Color) =
    drawBehind {
        drawIntoCanvas {
            val paint = Paint()
                .apply {
                    strokeWidth = width.toPx()
                    this.color = color
                    style = PaintingStyle.Stroke
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                }
            it.drawRoundRect(
                width.toPx(),
                width.toPx(),
                size.width - width.toPx(),
                size.height - width.toPx(),
                radius.toPx(),
                radius.toPx(),
                paint
            )
        }
    }

