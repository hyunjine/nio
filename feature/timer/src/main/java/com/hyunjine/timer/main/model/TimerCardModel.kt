package com.hyunjine.timer.main.model

import kotlin.time.Duration

data class TimerCardModel(
    val id: Int,
    val name: String,
    val duration: Duration,
    val state: TimerState
)