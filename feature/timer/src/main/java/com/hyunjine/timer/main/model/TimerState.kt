package com.hyunjine.timer.main.model

import kotlinx.serialization.Serializable

@Serializable
enum class TimerState {
    Running { override fun toggle(): TimerState = Paused },
    Paused { override fun toggle(): TimerState = Running };
    abstract fun toggle(): TimerState
}