package com.hyunjine.d_day.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
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
import dagger.hilt.android.EntryPointAccessors
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CounterWidget: GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        ).repository

        provideContent {
            LaunchedEffect(Unit) {
                repository.getStartDate().collect {
                    wlog(it)
                }
            }
            Column {
                Widget(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
            }
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
            .background(Color.Red)
            .padding(20.dp),
        style = TextStyle(
            color = ColorProvider(Color.Black),
            fontSize = 20.sp
        ),
    )
}