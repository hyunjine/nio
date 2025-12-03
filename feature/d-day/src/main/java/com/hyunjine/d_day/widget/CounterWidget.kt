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
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class CounterWidget: GlanceAppWidget() {
    private val getRepository: GetRepository by lazy {
        GetRepository()
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            var dDay by remember { mutableStateOf("") }
            LaunchedEffect(Unit) {
                getRepository(context).getStartDate().collect { startDate ->
                    dDay = ChronoUnit.DAYS.between(startDate, LocalDate.now()).plus(1).toString()
                }
            }
            AnniversaryWidgetContent("D+$dDay")
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@SuppressLint("RestrictedApi")
@Composable
@Preview()
fun Widget(text: String = "") {
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

@OptIn(ExperimentalGlancePreviewApi::class)
@SuppressLint("RestrictedApi")
@Composable
@Preview()
fun AnniversaryWidgetContent(
    dDay: String = "D+100",
    title: String = "Our Anniversary",
    date: String = "2023-01-01"
) {
    Column(
        modifier = GlanceModifier
            .fillMaxWidth()
            .background(Color(0x33222222)) // 가벼운 반투명 느낌
            .cornerRadius(24.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 우측 상단 설정 아이콘
//        Box(
//            modifier = GlanceModifier
//                .fillMaxWidth()
//        ) {
//            Image(
//                provider = ImageProvider(Icons.Default.Settings),
//                contentDescription = "Settings",
//                modifier = GlanceModifier
//                    .size(18.dp)
//                    .align(Alignment.TopEnd)
//            )
//        }

        Text(
            text = dDay,
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = ColorProvider(Color.White)
            )
        )

        Spacer(GlanceModifier.height(8.dp))

        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                color = ColorProvider(Color.White.copy(alpha = 0.9f))
            )
        )

        Spacer(GlanceModifier.height(4.dp))

        Text(
            text = date,
            style = TextStyle(
                fontSize = 14.sp,
                color = ColorProvider(Color.White.copy(alpha = 0.8f))
            )
        )
    }
}


