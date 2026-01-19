package com.hyunjine.nio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.white
import com.hyunjine.timer.main.TimerMainScreen
import com.hyunjine.timer.running.TimerRunningScreen
import com.hyunjine.timer.setting.TimerSettingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NioTheme {
                TimerRunningScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(white)
                        .statusBarsPadding()
                )
//                TimerSettingScreen(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(white)
//                        .statusBarsPadding()
//                )
            }
        }
    }
}


