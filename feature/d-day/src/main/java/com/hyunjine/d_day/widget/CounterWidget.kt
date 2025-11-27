package com.hyunjine.d_day.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.hyunjine.common.log.wlog
import com.hyunjine.d_day.DDayRepository
import dagger.hilt.android.EntryPointAccessors
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CounterWidget: GlanceAppWidget() {
    private val getRepository: GetRepository by lazy {
        GetRepository()
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        getRepository(context).updateStartDate(LocalDate.of(2025, 3, 8))

        provideContent {
            var dDay by remember { mutableStateOf("") }
            LaunchedEffect(Unit) {
                getRepository(context).getStartDate().collect { startDate ->
                    dDay = ChronoUnit.DAYS.between(startDate, LocalDate.now()).plus(1).toString()
                }
            }
            Widget("D+$dDay")
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@SuppressLint("RestrictedApi")
@Composable
@Preview()
fun Widget(text: String) {
    Text(
        text = text,
        modifier = GlanceModifier
            .background(Color.Gray)
            .cornerRadius(12.dp)
            .padding(20.dp),
        style = TextStyle(
            color = ColorProvider(Color.Black),
            fontSize = 20.sp
        ),
    )
}

