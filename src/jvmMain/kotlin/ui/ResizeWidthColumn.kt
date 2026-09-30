package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize

/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：ResizeWidthColumn
 *** date：2024/1/2  15:53
 *** filename：ResizeWidthColumn
 *** desc：调整宽度列的大小
 ***/

@Composable
fun ResizeWidthColumn(modifier: Modifier, resize: Boolean, mainContent: @Composable () -> Unit) {
    SubcomposeLayout(modifier) { constraints ->
        val mainPlaceables = subcompose(SlotsEnum.Main, mainContent).map {
            it.measure(Constraints())
        }

        val maxSize = mainPlaceables.fold(IntSize.Zero) { currentMax, placeable ->
            IntSize(
                width = maxOf(currentMax.width, placeable.width),
                height = maxOf(currentMax.height, placeable.height)
            )
        }

        val resizedPlaceables: List<Placeable> =
            subcompose(SlotsEnum.Dependent, mainContent).map {
                if (resize) {
                    it.measure(
                        Constraints(
                            minWidth = maxSize.width
                        )
                    )
                } else {
                    it.measure(Constraints())
                }
            }


        layout(constraints.maxWidth, constraints.maxHeight) {
            resizedPlaceables.forEachIndexed { index, placeable ->
                val widthStart = resizedPlaceables.take(index).sumOf { it.measuredHeight }
                placeable.place(0, widthStart)
            }
        }
    }
}