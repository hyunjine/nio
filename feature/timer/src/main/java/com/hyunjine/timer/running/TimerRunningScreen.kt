package com.hyunjine.timer.running

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.hyunjine.common.R
import com.hyunjine.common.extension.minutes
import com.hyunjine.common.extension.seconds
import com.hyunjine.common.extension.spToDp
import com.hyunjine.common.log.wlog
import com.hyunjine.common.ui.component.Appbar
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.black100
import com.hyunjine.common.ui.theme.black200
import com.hyunjine.common.ui.theme.black700
import com.hyunjine.common.ui.theme.black900
import com.hyunjine.common.ui.theme.blue900
import com.hyunjine.common.ui.theme.red600
import com.hyunjine.common.ui.theme.typography.typography
import com.hyunjine.common.ui.theme.white
import com.hyunjine.timer.main.model.TimerState
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Serializable
data class TimerRunningScreen(
//    val name: String,
    val state: TimerState,
//    val wholeDuration: Duration,
//    val duration: Duration,
) {
    @Composable
    operator fun invoke(
        viewModel: TimerRunningViewModel = hiltViewModel(
            creationCallback = { factory: TimerRunningViewModel.Factory ->
                factory.create(this)
            },
        ),
        onBack: () -> Unit = {},
    ) {
//        val mainTimer by viewModel.mainTimer.collectAsStateWithLifecycle()
//        val controlBox by viewModel.controlBox.collectAsStateWithLifecycle()
//
//        invoke(
//            mainTimer = mainTimer,
//            controlBox = controlBox,
//            onBack = onBack,
//            onClickRemove = {
//                viewModel.event(TimerRunningScreenEvent.RemoveTimer)
//            },
//            onClickControl = {
//                viewModel.event(TimerRunningScreenEvent.ToggleTimer(it))
//            }
//        )
    }

    @Composable
    operator fun invoke(
        mainTimer: MainTimer,
        controlBox: ControlBox,
        onBack: () -> Unit = {},
        onClickRemove: () -> Unit = {},
        onClickControl: (TimerState) -> Unit = {}
    ) {
        val scrollState = rememberScrollState() // 스크롤 상태 기억
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(white)
                .statusBarsPadding()
                .verticalScroll(scrollState),
        ) {
            Appbar(
                title = stringResource(R.string.timer_running_screen_appbar_title),
                onLeftIconClick = onBack
            )
            mainTimer(
                modifier = Modifier
                    .padding(horizontal = 22.dp)
            )
            Spacer(modifier = Modifier.weight(0.6F))
            controlBox(
                onClickRemove = onClickRemove,
                onClickControl = onClickControl,
            )
            Spacer(modifier = Modifier.weight(0.4F))
        }
    }
}

data class MainTimer(
    val name: String,
    @get:FloatRange(from = 0.0, to = 1.0)
    val progress: Float,
    val duration: Duration,
    val finishTime: LocalDateTime,
    val timerState: TimerState
) {
    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1F)
        ) {
            val (graphId, nameId, durationId, finishTimeId) = createRefs()
            val strokeWidth = 9.dp
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(graphId) {
                        top.linkTo(parent.top)
                    }
            ) {
                val strokeWidthPx = strokeWidth.toPx()
                val inset = strokeWidthPx / 2
                // 선이 차지하는 공간을 제외한 실제 원의 크기 계산
                val arcSize = Size(
                    width = size.width - strokeWidthPx,
                    height = size.height - strokeWidthPx
                )
                // 배경 회색 원
                drawCircle(
                    color = black100,
                    center = center, // 중앙 기준
                    radius = (size.minDimension - strokeWidthPx) / 2, // 반지름에서 두께 절반 빼기
                    style = Stroke(width = strokeWidthPx)
                )

                // 파란색 진행 선
                drawArc(
                    color = blue900,
                    startAngle = -90F,
                    sweepAngle = 360F * progress,
                    useCenter = false,
                    // 시작점을 (inset, inset)으로 이동시켜서 밖으로 나가지 않게 함
                    topLeft = Offset(inset, inset),
                    size = arcSize,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
                )
            }

            Text(
                text = String.format(Locale.getDefault(), "%02d:%02d", duration.minutes, duration.seconds),
                style = typography.displayLargeEmphasized,
                color = black900,
                modifier = Modifier
                    .constrainAs(durationId) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            )
            Text(
                text = name,
                style = typography.titleLargeEmphasized,
                color = black900,
                modifier = Modifier
                    .constrainAs(nameId) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(durationId.top, margin = 10.dp)
                        start.linkTo(parent.start)
                        verticalBias = 1F
                    }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .constrainAs(finishTimeId) {
                        top.linkTo(durationId.bottom, margin = 40.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        verticalBias = 0F
                    }
            ) {
                val formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.getDefault())
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(R.drawable.icon_14_bell),
                    contentDescription = null,
                    tint = black700
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = finishTime.format(formatter),
                    style = typography.labelLarge,
                    color = black700,
                )
            }

        }
    }
}

data class ControlBox(
    val timerState: TimerState
) {
    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        onClickRemove: () -> Unit = {},
        onClickControl: (TimerState) -> Unit = {}
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .widthIn(min = 120.spToDp)
                    .clip(RoundedCornerShape(23.dp))
                    .clickable { onClickRemove() }
                    .background(color = black200)
                    .padding(vertical = 13.spToDp),
                text = stringResource(R.string.timer_running_screen_timer_remove),
                style = typography.labelLargeEmphasized,
                color = black900,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(20.dp))
            val (background, textId, color) = when (timerState) {
                TimerState.Running -> {
                    Triple(
                        red600, R.string.timer_running_screen_timer_pause, white
                    )
                }
                TimerState.Paused -> {
                    Triple(
                        blue900, R.string.timer_running_screen_timer_resume, white
                    )
                }
            }
            Text(
                modifier = Modifier
                    .widthIn(min = 120.spToDp)
                    .clip(RoundedCornerShape(23.dp))
                    .clickable { onClickControl(timerState) }
                    .background(color = background)
                    .padding(vertical = 13.spToDp),
                text = stringResource(textId),
                style = typography.labelLargeEmphasized,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class SubTimer(
    val timerState: TimerState
) {
    @Composable
    operator fun invoke() {

    }
}

@Composable
@Preview(showBackground = true)
fun NonSubTimerPreview() {
    NioTheme {
        TimerRunningScreen(TimerState.Running)(
            mainTimer = MainTimer(
                name = "name",
                progress = 0.5F,
                duration = 10.minutes,
                finishTime = LocalDateTime.now(),
                timerState = TimerState.Running
            ),
            controlBox = ControlBox(
                timerState = TimerState.Running
            )
        )
    }
}