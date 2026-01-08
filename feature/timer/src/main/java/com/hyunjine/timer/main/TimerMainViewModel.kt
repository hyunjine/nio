package com.hyunjine.timer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyunjine.timer.main.model.TimerCardModel
import com.hyunjine.timer.main.model.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class TimerMainViewModel @Inject constructor(): ViewModel() {
    val timerCards: StateFlow<List<TimerCardModel>> = flow {
        val items = List(30) { index ->
            TimerCardModel(
                id = index,
                name = "라면",
                duration = 3.toDuration(DurationUnit.MINUTES) + 30.toDuration(DurationUnit.SECONDS),
                state = TimerState.IDLE
            )
        }
        emit(items)
        delay(1000L)
        emit(emptyList())
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = listOf())
}