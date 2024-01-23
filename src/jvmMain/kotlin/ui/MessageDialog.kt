package ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


/****
 *** author：lao
 *** package：ui
 *** project：CFPurgatory
 *** name：MessageDialog
 *** date：2024/1/3  14:37
 *** filename：MessageDialog
 *** desc：
 ***/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageDialog(alertDialog: MutableState<Boolean>, onClose: () -> Unit, title : String, message : String) {
    if (alertDialog.value) {
        AlertDialog(
            onDismissRequest = { alertDialog.value = false },
//            modifier = Modifier.background(color = Color(0x21378590), shape = RoundedCornerShape(10.dp)),
            confirmButton = {
                TextButton(
                    onClick = {
                        alertDialog.value = false

                    }
                ) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        alertDialog.value = false
                    }
                ) {
                    Text("取消")
                }
            },
            text = { Text(text = message) },
            backgroundColor = Color.Transparent,
            contentColor = Color.Red,
            shape = RoundedCornerShape(50.dp),
        )
    }
}
