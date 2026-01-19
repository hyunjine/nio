package com.hyunjine.timer.setting

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjine.common.R
import com.hyunjine.common.log.wlog
import com.hyunjine.common.ui.component.Appbar
import com.hyunjine.common.ui.component.TimePicker
import com.hyunjine.common.ui.component.VerticalWheelPicker
import com.hyunjine.common.ui.theme.NioTheme
import kotlin.time.Duration

sealed interface TimerSettingScreen {
    companion object {
        @Composable operator fun invoke(
            modifier: Modifier = Modifier,
            onBack: () -> Unit = {},
        ) {
            Row(
                modifier = modifier
            ) {
                Appbar(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.timer_setting_screen_appbar_title),
                    rightText = stringResource(R.string.timer_setting_screen_appbar_right_text),
                    onLeftIconClick = {  },
                    onRightTextClick = onBack
                )
            }
        }
    }

    open class NonSubTimer(
        val timerDuration: Duration,
        val timerName: String
    ): TimerSettingScreen {

    }

    data class SubTimer(
        val timerDuration: Duration,
        val timerName: String
    ): TimerSettingScreen {

    }
}

@Composable
@Preview(showBackground = true)
fun TimerSettingScreenPreview() {
    NioTheme {
        TimerSettingScreen(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
        )
    }
}