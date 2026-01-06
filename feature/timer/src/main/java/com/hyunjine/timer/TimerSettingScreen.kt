package com.hyunjine.timer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hyunjine.common.ui.theme.typography.typography

@Composable
fun TimerSettingScreen(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            textStyle = typography.displayLarge,
            value = "",
            onValueChange = { },
            label = { Text(text = "제목") }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TimerSettingScreenPreview() {
    TimerSettingScreen(Modifier.fillMaxSize())
}