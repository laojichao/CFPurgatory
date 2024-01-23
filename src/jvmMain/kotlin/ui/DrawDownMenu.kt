package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/****
 *** author：lao
 *** package：ui
 *** project：CFPurgatory
 *** name：DrawDownMenu
 *** date：2024/1/2  22:19
 *** filename：DrawDownMenu
 *** desc：下拉菜单
 ***/

@Composable
fun dropDownMenu(items: List<String>, onSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(config!!.xbutton) }

    val inlineContentId = "inlineContentId"
    val secondayText = buildAnnotatedString {
        append(items[selectedIndex])
        appendInlineContent(inlineContentId, "[icon]")
    }

    val inlineContent = mapOf(
        Pair(inlineContentId,
            InlineTextContent(
                Placeholder(width = 14.sp,
                    height = 14.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center)
            ) { str ->
                when (str) {
                    "[icon]" -> Icon(painter = painterResource("arrow.svg"),
                        contentDescription = null)
                }

            })
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
        .background(Color(0xFFDDEEF4))
        .clip(RoundedCornerShape(10.dp)) // 设置圆角半径
    ) {
        //显示选中
        Text(text = secondayText,
            inlineContent = inlineContent,
            style = textStyle,
            modifier = Modifier.clickable ( onClick = { expanded = true } ).padding(10.dp)
        )
        //下拉选项
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0x5532ff7e),
                        Color(0x553ae374),
                        Color(0x557e55f5),
                        Color(0x5567e6dc),
                        Color(0x5518dcff),
                        Color(0x5518dcff),
                        Color(0x557d5fff),
                        Color(0x557158e2)
                    )))
                .width(180.dp)
                .height(100.dp)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    onSelected(index)
                    expanded = false
                }) {
                    val disabledText = if (s == disabledValue) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(AnnotatedString(s + disabledText) , style = textStyle)
                }
            }
        }
    }

}