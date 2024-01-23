package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import bean.RxCode
import com.sun.jna.platform.win32.Advapi32Util
import com.sun.jna.platform.win32.WinReg
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import utils.MachineCodeUtil
import utils.RSAUtils
import widget.EditText

/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：ActiveCodeDialog
 *** date：2024/1/2  16:21
 *** filename：ActiveCodeDialog
 *** desc：激活码输入框
 ***/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActiveCodeDialog(dialogState: MutableState<Boolean>, onClose: () -> Unit) {
    var activeCode by remember { mutableStateOf("") }
    var title = "激活码"
    if (dialogState.value) {
        Dialog(
            onCloseRequest = {
                dialogState.value = false
            },
            state = DialogState(
                size = DpSize(200.dp, 150.dp)
            ),

            title = title,
        ) {
            Card (modifier = Modifier.fillMaxSize().padding(5.dp).background(color = Color.Blue)) {
                Column {

                    Column(modifier = Modifier.fillMaxWidth().padding(5.dp, 5.dp, 5.dp, 5.dp)) {
                        EditText(
                            value = activeCode,
                            modifier = Modifier.fillMaxWidth().height(25.dp),
                            onValueChange = {
                                activeCode = it
                            },
                            placeholder = {Text(text = "请输入激活码:", style = textStyle)},
                            singleLine = true
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(0.dp, 5.dp, 0.dp, 0.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                dialogState.value = false
                                onClose
                            }
                        ) {
                            Text(text = "取消", style = textStyle)
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            onClick = {
                                permanentActive(activeCode)
                                dialogState.value = false
                                onClose
                            }
                        ) {
                            Text(text = "激活", style = textStyle)
                        }
                    }
                }
            }
        }
    }

}


/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：permanentActive
 *** desc：永久激活码
 ***/
fun permanentActive(activeCode: String) {
    println("永久激活码写注册表")
    //匹配激活码写到注册表
    val runPath = "Software\\AutoTech\\CFPurgatory"
    val strValue = "License"
    val runPathExist = Advapi32Util.registryKeyExists(WinReg.HKEY_CURRENT_USER, runPath)
    if (!runPathExist) {
        Advapi32Util.registryCreateKey(WinReg.HKEY_CURRENT_USER, runPath)
        println("创建注册表成功");
    }
    val isExists = Advapi32Util.registryValueExists(WinReg.HKEY_CURRENT_USER,runPath,strValue)
    println(isExists)
    if (isExists) {
        println("已存在键值，查看是否覆盖");
        //获取注册表中CFPurgatory的具体值
        val value = Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER,runPath,strValue)
        if (!value.equals(activeCode)) {
            //不相同则覆盖
            println("激活码不相同则覆盖");
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, runPath, strValue, activeCode)
        }
    } else {
        Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, runPath, strValue, activeCode)
        println("不存在键值，直接写入激活码");
    }
    checkMachineCode(activeCode)
}

/**
 * 永久激活 读取激活码
 */
fun readActiveCode() {
    val runPath = "Software\\AutoTech\\CFPurgatory"
    val strValue = "License"
    //获取注册表中License的具体值
    val exist = Advapi32Util.registryValueExists(WinReg.HKEY_CURRENT_USER,runPath,strValue)
    if (exist) {
        val activeCode = Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER,runPath,strValue)
        println("读取的激活码为：$activeCode")
        checkMachineCode(activeCode)
    }


}


fun checkMachineCode(activeCode: String) {
    val machineCode = RSAUtils.decrypt(activeCode)
    val localmachineCode = MachineCodeUtil.getThisMachineCodeMd5()
//    println("检查机器码$machineCode")
}
