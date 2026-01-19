package com.hyunjine.common.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.black900
import com.hyunjine.common.ui.theme.typography.typography
import com.hyunjine.common.extension.minutes
import com.hyunjine.common.extension.seconds
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * 시, 분, 초를 선택할 수 있는 시간 선택기 컴포저블입니다.
 * [VerticalWheelPicker]를 조합하여 구성되며, 선택된 시간은 [Duration] 형태로 반환됩니다.
 *
 * @param hourOption 시간(Hour) 선택 여부를 결정하는 플래그입니다. true일 경우 시간 선택 휠이 표시됩니다.
 * @param initDuration 피커가 처음 생성될 때 표시할 초기 시간 값입니다. (최대 100시간으로 제한됩니다.)
 * @param onTimeSelected 선택된 시간이 변경될 때 호출되는 콜백 함수입니다.
 */
@Composable fun TimePicker(
    modifier: Modifier = Modifier,
    hourOption: Boolean = false,
    initDuration: Duration = Duration.ZERO,
    onTimeSelected: (Duration) -> Unit = {}
) {
    val initDuration = initDuration.coerceAtMost(100.hours)
    var hours by remember { mutableIntStateOf(initDuration.inWholeHours.toInt()) }
    var minutes by remember { mutableIntStateOf(initDuration.minutes.toInt()) }
    var seconds by remember { mutableIntStateOf(initDuration.seconds.toInt()) }

    val duration = hours.hours + minutes.minutes + seconds.seconds
    LaunchedEffect(duration) {
        onTimeSelected(duration)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        if (hourOption) {
            VerticalWheelPicker(
                items = List(100) { Time(value = it) },
                onItemSelected = { hours = it.value }
            )
            TimePickerSeparator()
        }
        VerticalWheelPicker(
            items = List(60) { Time(value = it) },
            onItemSelected = { minutes = it.value }
        )
        TimePickerSeparator()
        VerticalWheelPicker(
            items = List(60) { Time(value = it) },
            onItemSelected = { seconds = it.value }
        )
    }
}

@Composable
private fun TimePickerSeparator() {
    Text(
        style = typography.displayMediumEmphasized,
        color = black900,
        text = ":"
    )
}

@Composable
@Preview(showBackground = true)
fun TimePickerHourOptionPreview() {
    NioTheme {
        TimePicker(hourOption = true)
    }
}

@Composable
@Preview(showBackground = true)
fun TimePickerNonHourOptionPreview() {
    NioTheme {
        TimePicker(hourOption = false)
    }
}

private data class Time(
    val value: Int,
    override val name: String = String.format(Locale.getDefault(), "%02d", value)
): VerticalWheelPicker