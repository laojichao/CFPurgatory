package ui

import com.sun.jna.platform.win32.Advapi32Util
import com.sun.jna.platform.win32.WinReg
import java.io.File

/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：AutoRun
 *** date：2024/1/2  16:46
 *** filename：AutoRun
 *** desc：开机自启动
 ***/
    
fun AutoRun(autoRun: Boolean) {
    //                        val path = "D:\\Program Files\\CFPurgatory\\CFPurgatory.exe"
    val currentPath = File("").absolutePath;//运行目录
    println(currentPath)
    val path = "$currentPath\\CFPurgatory.exe"
    val runPath = "Software\\Microsoft\\Windows\\CurrentVersion\\Run"
    val strValue = "CFPurgatory"
    val isExists = Advapi32Util.registryValueExists(WinReg.HKEY_CURRENT_USER, runPath, strValue)
    println(isExists)
    if (autoRun) {
        // 设置程序的自启动信息
        //获取实际的程序安装目录
        if (isExists) {
            logger.info("已存在,是否覆盖");
            //获取注册表中CFPurgatory的具体值
            val value =
                Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, runPath, strValue)
            if (!value.equals(path)) {
                //不相同则覆盖
                logger.info("不相同则覆盖");
                Advapi32Util.registrySetStringValue(
                    WinReg.HKEY_CURRENT_USER, runPath, strValue, path
                )
            }
        } else {
//                                Advapi32Util.registryCreateKey(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run")
            Advapi32Util.registrySetStringValue(
                WinReg.HKEY_CURRENT_USER,
                runPath,
                strValue,
                path
            )
            logger.info("不存在直接写入自启动");
        }
    } else {
        //删除
        if (isExists) {
            logger.info("存在直接删除");
            Advapi32Util.registryDeleteValue(WinReg.HKEY_CURRENT_USER, runPath, strValue)
        }
    }

}