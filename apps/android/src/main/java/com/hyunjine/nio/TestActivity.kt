package com.hyunjine.nio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.timer.main.model.TimerState
import com.hyunjine.timer.running.TimerRunningScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.Duration.Companion.minutes

@AndroidEntryPoint
class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NioTheme {
                TimerRunningScreen(
                    TimerState.Running(10.minutes)
                ).invoke()
            }
        }
    }
}


