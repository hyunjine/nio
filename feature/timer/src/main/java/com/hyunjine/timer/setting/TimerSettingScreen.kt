package com.hyunjine.timer.setting

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjine.common.log.wlog
import com.hyunjine.common.ui.component.VerticalWheelPicker

@Composable
fun TimerSettingScreen(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Spacer(Modifier.height(250.dp))
        VerticalWheelPicker(
            items = List(90) { "${it.inc()}" },
            onItemSelected = {
                wlog(it)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TimerSettingScreenPreview() {
    TimerSettingScreen(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    )
}