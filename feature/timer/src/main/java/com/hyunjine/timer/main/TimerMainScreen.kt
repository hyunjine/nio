package com.hyunjine.timer.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyunjine.common.R
import com.hyunjine.common.extension.minutes
import com.hyunjine.common.extension.plus
import com.hyunjine.common.extension.seconds
import com.hyunjine.common.ui.component.Appbar
import com.hyunjine.common.ui.component.TouchBox
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.black100
import com.hyunjine.common.ui.theme.black600
import com.hyunjine.common.ui.theme.black700
import com.hyunjine.common.ui.theme.black800
import com.hyunjine.common.ui.theme.black900
import com.hyunjine.common.ui.theme.typography.typography
import com.hyunjine.common.ui.theme.white
import com.hyunjine.timer.main.model.TimerCardModel
import com.hyunjine.timer.main.model.TimerState
import java.util.Locale
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun TimerMainScreen(
    modifier: Modifier = Modifier,
    viewModel: TimerMainViewModel = hiltViewModel(),
    onBack: () -> Unit = { },
    onTimerSelected: (TimerCardModel) -> Unit = {}
) {
    val items by viewModel.timerCards.collectAsStateWithLifecycle()
    TimerMainScreen(
        modifier = modifier,
        items = items,
        onBack = onBack,
        onTimerSelected = onTimerSelected
    )
}

@Composable
fun TimerMainScreen(
    modifier: Modifier = Modifier,
    items: List<TimerCardModel>,
    onBack: () -> Unit = {},
    onTimerSelected: (TimerCardModel) -> Unit = {}
) {
    Column(modifier = modifier) {
        Appbar(
            title = stringResource(R.string.timer_main_screen_appbar_title),
            rightText = stringResource(R.string.timer_main_screen_appbar_right_text),
            onLeftIconClick = onBack,
            onRightTextClick = {

            }
        )
        if (items.isEmpty()) {
            Spacer(modifier = Modifier.weight(4F))
            Text(
                text = stringResource(R.string.timer_main_screen_empty_list),
                style = typography.titleSmall,
                color = black600,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(6F))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 22.dp, end = 22.dp, top = 22.dp, bottom = 40.dp) + WindowInsets.navigationBars.asPaddingValues(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                overscrollEffect = null
            ) {
                items(
                    items = items,
                    key = { item -> item.id }
                ) { item ->
                    TimerCard(
                        model = item,
                        onClick = onTimerSelected
                    )
                }
            }
        }
    }
}

@Composable
fun TimerCard(
    model: TimerCardModel,
    modifier: Modifier = Modifier,
    onClick: (TimerCardModel) -> Unit = {}
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(black100)
            .clickable { onClick(model) },
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
            ) {
                Text(
                    text = model.name,
                    style = typography.titleMediumEmphasized,
                    color = black900,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = String.format(Locale.getDefault(), "%02d:%02d", model.duration.minutes, model.duration.seconds),
                    style = typography.titleSmall,
                    color = black800,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            TouchBox(
                modifier = Modifier.size(24.dp),
                onClick = { }
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.icon_16_more),
                    contentDescription = null,
                    tint = black700
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            modifier = Modifier
                .padding(end = 10.dp, bottom = 10.dp)
                .size(52.dp)
                .align(Alignment.End),
            painter = painterResource(R.drawable.icon_34_fb_resume),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TimerScreenPreview() {
    NioTheme {
        TimerMainScreen(
            modifier = Modifier.background(white),
            items = List(10) { index ->
                TimerCardModel(
                    id = index,
                    name = "Timer $index".repeat(4),
                    duration = 30.toDuration(DurationUnit.DAYS) + 30.toDuration(DurationUnit.SECONDS),
                    state = TimerState.Paused
                )
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TimerScreenEmptyPreview() {
    NioTheme {
        TimerMainScreen(
            modifier = Modifier.background(white),
            items = emptyList()
        )
    }
}