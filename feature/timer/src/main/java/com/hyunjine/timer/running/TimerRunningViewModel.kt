package com.hyunjine.timer.running

import androidx.lifecycle.viewModelScope
import com.hyunjine.common.log.wlog
import com.hyunjine.common.util.BaseViewModel
import com.hyunjine.timer.main.model.TimerState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx3.asFlow
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.inc
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel(assistedFactory = TimerRunningViewModel.Factory::class)
class TimerRunningViewModel @AssistedInject constructor(
    @Assisted arg: TimerRunningScreen
) : BaseViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(timerRunningScreen: TimerRunningScreen): TimerRunningViewModel
    }

    private val uiEvent = MutableSharedFlow<TimerRunningScreenEvent>()


//    @OptIn(ExperimentalCoroutinesApi::class)
//    val timerState: StateFlow<TimerState> = merge(
//        flowOf(if (arg.timerState is TimerState.Paused) arg.timerState.toggle() else arg.timerState),
//        uiEvent
//            .filterIsInstance<TimerRunningScreenEvent.ToggleTimer>()
//            .map { it.timerState.toggle() }
//    ).flatMapLatest { state ->
//        when (state) {
//            is TimerState.Running -> Observable
//                .interval(1, TimeUnit.SECONDS)
//                .asFlow()
//            is TimerState.Paused -> emptyFlow()
//        }.runningFold(arg.timerState.duration.inWholeSeconds) { lastValue, diff ->
//            lastValue - diff
//        }.takeWhile {
//            it >= 0
//        }.map {
//            when (state) {
//                is TimerState.Running -> TimerState.Running(it.seconds)
//                is TimerState.Paused -> TimerState.Paused(it.seconds)
//            }
//        }
//        emptyFlow<TimerState>()
//    }.stateIn(if (arg.timerState is TimerState.Paused) arg.timerState.toggle() else arg.timerState)
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    val mainTimer: StateFlow<MainTimer> = timerState
//        .mapLatest { state ->
//            val progress = (state.duration / arg.timerState.duration).toFloat()
//            when (state) {
//                is TimerState.Running -> MainTimer(
//                    name = "Burt Padilla",
//                    progress = progress,
//                    finishTime = LocalDateTime.now(),
//                    timerState = state
//                )
//                is TimerState.Paused -> MainTimer(
//                    name = "Burt Padilla",
//                    progress = progress,
//                    finishTime = LocalDateTime.now(),
//                    timerState = state
//                )
//            }
//        }.stateIn(
//            MainTimer(
//                name = "Burt Padilla",
//                finishTime = LocalDateTime.now(),
//                timerState = TimerState.Running(10.seconds),
//                progress = 0.5F,
//            )
//        )
//
//    val controlBox: StateFlow<ControlBox> = timerState
//        .map { ControlBox(timerState = it) }
//        .stateIn(ControlBox(timerState = timerState.value))

    fun event(event: TimerRunningScreenEvent) = viewModelScope.launch {
        uiEvent.emit(event)
    }
}