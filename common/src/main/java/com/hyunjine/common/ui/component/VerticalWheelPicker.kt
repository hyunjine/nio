package com.hyunjine.common.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjine.common.ui.theme.black200
import com.hyunjine.common.ui.theme.black900
import com.hyunjine.common.ui.theme.typography.typography
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalWheelPicker(
    items: List<String>,
    displayItemCount: Int = 3,
    spacing: Dp = 22.dp,
    onItemSelected: (String) -> Unit
) {
    val repetition = Int.MAX_VALUE / items.size
    val initialIndex = items.size * repetition.div(2)
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState, snapPosition = SnapPosition.Center)

    val height = with(LocalDensity.current) {
        typography.displayMedium.lineHeight.toDp() * displayItemCount
    }.plus(
        spacing * displayItemCount.dec().coerceAtLeast(0)
    )

    Column(modifier = Modifier.height(height)) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing),
            modifier = Modifier.fillMaxSize()
        ) {
            items(Int.MAX_VALUE) { index ->
                val item = items[index % items.size]

                val isSelected by remember {
                    derivedStateOf {
                        val layoutInfo = listState.layoutInfo
                        val center = layoutInfo.viewportEndOffset / 2
                        val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
                        itemInfo?.let {
                            val itemCenter = it.offset + (it.size / 2)
                            abs(center - itemCenter) < it.size / 2
                        } ?: false
                    }
                }

                if (isSelected) {
                    onItemSelected(item)
                }

                Text(
                    text = item,
                    style = if (isSelected) typography.displayMediumEmphasized else typography.displayMedium,
                    color = if (isSelected) black900 else black200
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun VerticalWheelPickerPreview() {
    VerticalWheelPicker(
        items = List(60) { "${it.inc()}" },
        onItemSelected = {

        }
    )
}