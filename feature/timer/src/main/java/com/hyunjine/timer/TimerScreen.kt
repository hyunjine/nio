package com.hyunjine.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hyunjine.common.ui.component.Appbar
import com.hyunjine.common.ui.theme.NioTheme

@Composable
fun TimerScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Appbar(
            title = "타이머",
            rightText = "추가",
            onLeftIconClick = {

            },
            onRightTextClick = {

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