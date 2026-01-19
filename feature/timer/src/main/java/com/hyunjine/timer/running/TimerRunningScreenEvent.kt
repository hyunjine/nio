package com.hyunjine.timer.running

sealed interface TimerRunningScreenEvent {
    data object ResumeTimer : TimerRunningScreenEvent
    data object RemoveTimer : TimerRunningScreenEvent
    data object PauseTimer : TimerRunningScreenEvent
}