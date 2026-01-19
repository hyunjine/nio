package com.hyunjine.timer.running

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjine.timer.main.model.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class TimerRunningViewModel @Inject constructor(): ViewModel() {
    private val uiEvent = MutableSharedFlow<TimerRunningScreenEvent>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val mainTimer = uiEvent.mapLatest { event ->
        when (event) {
            TimerRunningScreenEvent.ResumeTimer -> MainTimer(
                name = "Burt Padilla",
                progress = 0.5F,
                duration = 10.seconds,
                finishTime = LocalDateTime.now(),
                timerState = TimerState.Running
            )
            else -> {
                MainTimer(
                    name = "Burt Padilla",
                    progress = 0.5F,
                    duration = 10.seconds,
                    finishTime = LocalDateTime.now(),
                    timerState = TimerState.Running
                )
            }
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = MainTimer(
        name = "Burt Padilla",
        duration = 10.seconds,
        finishTime = LocalDateTime.now(),
        timerState = TimerState.Running,
        progress = 0.5F,
    ))

    fun event(event: TimerRunningScreenEvent) = viewModelScope.launch {
        uiEvent.emit(event)
    }
}