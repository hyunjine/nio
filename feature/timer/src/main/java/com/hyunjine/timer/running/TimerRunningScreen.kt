package com.hyunjine.timer.running

import android.R.attr.strokeWidth
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyunjine.common.R
import com.hyunjine.common.extension.minutes
import com.hyunjine.common.extension.seconds
import com.hyunjine.common.log.wlog
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.black100
import com.hyunjine.common.ui.theme.black700
import com.hyunjine.common.ui.theme.black900
import com.hyunjine.common.ui.theme.blue900
import com.hyunjine.common.ui.theme.typography.typography
import com.hyunjine.timer.main.model.TimerState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Composable
fun TimerRunningScreen(
    modifier: Modifier = Modifier,
    viewModel: TimerRunningViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {
    val mainTimer by viewModel.mainTimer.collectAsStateWithLifecycle()
    TimerRunningScreen(
        modifier = modifier
            .padding(horizontal = 22.dp),
        mainTimer = mainTimer,
        onBack = onBack
    )
}

@Composable
fun TimerRunningScreen(
    modifier: Modifier = Modifier,
    mainTimer: MainTimer,
    onBack: () -> Unit = {},
) {
    Row(
        modifier = modifier
    ) {
        mainTimer()
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
            modifier = Modifier
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
                        top.linkTo(durationId.bottom, margin = 41.dp)
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
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = finishTime.format(formatter),
                    style = typography.labelMedium,
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
    operator fun invoke() {

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
        TimerRunningScreen(
            mainTimer = MainTimer(
                name = "name",
                progress = 0.5F,
                duration = 10.minutes,
                finishTime = LocalDateTime.now(),
                timerState = TimerState.Running
            ),
        )
    }
}