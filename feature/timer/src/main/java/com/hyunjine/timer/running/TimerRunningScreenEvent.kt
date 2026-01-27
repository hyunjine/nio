package com.hyunjine.timer.running

import com.hyunjine.timer.main.model.TimerState

sealed interface TimerRunningScreenEvent {
    data class ToggleTimer(val timerState: TimerState) : TimerRunningScreenEvent
    data object RemoveTimer : TimerRunningScreenEvent
}