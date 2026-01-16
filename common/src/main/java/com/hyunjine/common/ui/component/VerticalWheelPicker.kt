package com.hyunjine.common.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjine.common.log.wlog
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.black200
import com.hyunjine.common.ui.theme.black900
import com.hyunjine.common.ui.theme.typography.typography
import kotlin.math.abs

/**
 * 세로 방향으로 아이템을 선택할 수 있는 휠 피커 컴포저블입니다.
 * [Int.MAX_VALUE]를 활용하여 가상 무한 스크롤을 지원하며, 중앙에 위치한 아이템을 자동으로 스냅(Snap)합니다.
 *
 * @param T [VerticalWheelPicker] 인터페이스를 상속받은 데이터 타입입니다.
 * @param items 피커에 표시될 아이템 리스트입니다.
 * @param displayItemCount 한 화면에 동시에 보여줄 아이템의 개수입니다. (기본값: 3)
 * @param spacing 아이템 간의 세로 간격입니다. (기본값: 22.dp)
 * @param onItemSelected 특정 아이템이 중앙에 위치하여 선택될 때마다 호출되는 콜백입니다.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable fun <T : VerticalWheelPicker> VerticalWheelPicker(
    items: List<T>,
    displayItemCount: Int = 3,
    spacing: Dp = 22.dp,
    onItemSelected: (T) -> Unit = {}
) {
    // 무한 스크롤 지원을 위해 size를 max로 설정
    val repetition = Int.MAX_VALUE / items.size
    // 첫 index 위치를 max의 절반에 위치시켜 위 아래 무한 스크롤 가능
    val initialIndex = items.size * repetition.div(2)
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState, snapPosition = SnapPosition.Center)

    val height = with(LocalDensity.current) {
        typography.displayMedium.lineHeight.toDp() * displayItemCount
    }.plus(
        spacing * displayItemCount.dec().coerceAtLeast(0)
    )

    val currentSelectedIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val center = layoutInfo.viewportEndOffset / 2

            layoutInfo.visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + (item.size / 2)
                abs(center - itemCenter)
            }?.index ?: initialIndex
        }
    }

    LaunchedEffect(currentSelectedIndex, onItemSelected) {
        onItemSelected(items[currentSelectedIndex.dec() % items.size])
    }

    LazyColumn(
        modifier = Modifier.height(height),
        state = listState,
        flingBehavior = flingBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing),
    ) {
        items(Int.MAX_VALUE) { index ->
            val item = items[index.dec() % items.size]
            val isSelected = index == currentSelectedIndex

            Text(
                text = item.name,
                style = if (isSelected) typography.displayMediumEmphasized else typography.displayMedium,
                color = if (isSelected) black900 else black200
            )
        }
    }
}

@Preview(showBackground = true)
@Composable fun VerticalWheelPickerPreview() {
    NioTheme {
        VerticalWheelPicker(
            items = List(60) {
                object : VerticalWheelPicker {
                    override val name: String = "${it.inc()}"
                }
            },
            onItemSelected = {

            }
        )
    }
}

interface VerticalWheelPicker {
    val name: String
}