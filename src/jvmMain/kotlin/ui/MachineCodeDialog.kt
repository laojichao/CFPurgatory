package ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import utils.MachineCodeUtil

/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：MachineCodeDialog
 *** date：2024/1/2  16:00
 *** filename：MachineCodeDialog
 *** desc：这个是一个标准的dialog 弹框
 ***/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MachineCodeDialog(alertDialog: MutableState<Boolean>, onClose: () -> Unit, clipboardManager: ClipboardManager) {
    var code = MachineCodeUtil.getThisMachineCodeMd5()
    //只显示机器码，拿到机器码后，我们再加密给用户
//                                code = utils.RSAUtils.encrypt(code)
    if (alertDialog.value) {
        AlertDialog(
            onDismissRequest = { alertDialog.value = false },
            title = { Text(text = "机器码", style = textStyle) },
            text = {
                val length = code.length
                val str = code.substring(0, length / 2) + "\n" + code.substring(length / 2 , length)
                Text(text = str, style = textStyle)
            }, confirmButton = {
                Button(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(code))
                        alertDialog.value = false
                        onClose
                    }
                ) {
                    Text(text = "复制", style = textStyle)
                }

            }, dismissButton = {
                Button(onClick = {
                    alertDialog.value = false
                    onClose
                }) {
                    Text(text = "取消", style = textStyle)
                }
            })
    }
}


