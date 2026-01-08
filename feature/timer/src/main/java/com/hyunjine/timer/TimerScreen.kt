package com.hyunjine.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hyunjine.common.log.wlog
import com.hyunjine.common.ui.component.Appbar
import com.hyunjine.common.ui.theme.NioTheme

@Composable
fun TimerScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Appbar(
            title = "타이머".repeat(2),
            rightText = "추가".repeat(2),
            onLeftIconClick = {
                wlog("onLeftIconClick")
            },
            onRightTextClick = {
                wlog("onRightTextClick")
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TimerScreenPreview() {
    NioTheme {
        TimerScreen()
    }
}