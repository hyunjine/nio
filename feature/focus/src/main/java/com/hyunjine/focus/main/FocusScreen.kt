package com.hyunjine.focus.main

import android.app.TimePickerDialog
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun FocusScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    })
    if (!Settings.canDrawOverlays(context)) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:${context.packageName}".toUri()
        )
        context.startActivity(intent)
    }

    var timeText by remember { mutableStateOf("Select Time") }

    Button(onClick = {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                timeText = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
            },
            LocalDateTime.now().hour,
            LocalDateTime.now().minute,
            true
        )
        timePickerDialog.show()
    }) {
        Text(timeText)
    }
}

/**
 * @param label 좌측 상단에 표시할 라벨입니다.
 * @param date 처음으로 세팅할 날짜입니다.
 */
@Composable
fun FocusTimeSetting(
    label: String,
    date: LocalDateTime
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label)
    }
}


/**
 * A screen for setting start and end times for a focus session.
 */
@Composable
fun FocusTimerScreen(modifier: Modifier = Modifier) {
    var startTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var endTime by remember { mutableStateOf(LocalTime.of(17, 0)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // --- Start Time Section ---
        Text(
            "Start Time",
            style = MaterialTheme.typography.titleLarge
        )
        TimeSelector(
            time = startTime,
            onTimeChange = { newTime -> startTime = newTime }
        )

        // --- End Time Section ---
        Text(
            "End Time",
            style = MaterialTheme.typography.titleLarge
        )
        TimeSelector(
            time = endTime,
            onTimeChange = { newTime -> endTime = newTime }
        )
    }
}

/**
 * A composable for selecting a time (hour and minute) with dropdowns
 * and quick-add buttons.
 *
 * @param time The current time to display.
 * @param onTimeChange A callback that is invoked when the time is changed.
 */
@Composable
fun TimeSelector(
    time: LocalTime,
    onTimeChange: (LocalTime) -> Unit
) {
    var hourSelectorExpanded by remember { mutableStateOf(false) }
    var minuteSelectorExpanded by remember { mutableStateOf(false) }

    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val hours = (0..23).toList()
    val minutes = (0..59 step 5).toList()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // --- Time Display and Pickers ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Hour Selector
            Box {
                Button(onClick = { hourSelectorExpanded = true }) {
                    Text(time.format(timeFormatter).substring(0, 2))
                }
                DropdownMenu(
                    expanded = hourSelectorExpanded,
                    onDismissRequest = { hourSelectorExpanded = false }
                ) {
                    hours.forEach { hour ->
                        DropdownMenuItem(
                            text = { Text(hour.toString().padStart(2, '0')) },
                            onClick = {
                                onTimeChange(time.withHour(hour))
                                hourSelectorExpanded = false
                            }
                        )
                    }
                }
            }

            Text(":", style = MaterialTheme.typography.titleLarge)

            // Minute Selector
            Box {
                Button(onClick = { minuteSelectorExpanded = true }) {
                    Text(time.format(timeFormatter).substring(3, 5))
                }
                DropdownMenu(
                    expanded = minuteSelectorExpanded,
                    onDismissRequest = { minuteSelectorExpanded = false }
                ) {
                    minutes.forEach { minute ->
                        DropdownMenuItem(
                            text = { Text(minute.toString().padStart(2, '0')) },
                            onClick = {
                                onTimeChange(time.withMinute(minute))
                                minuteSelectorExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // --- Quick Add Buttons ---
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Button(onClick = { onTimeChange(time.plusMinutes(10)) }) {
                Text("+10m")
            }
            Button(onClick = { onTimeChange(time.plusMinutes(30)) }) {
                Text("+30m")
            }
            Button(onClick = { onTimeChange(time.plusHours(1)) }) {
                Text("+1h")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun FocusTimerScreenPreview() {
    // Assuming you have a MaterialTheme set up in your project
    // For preview purposes, a basic Surface is used.
    Surface(color = MaterialTheme.colorScheme.background) {
        FocusTimerScreen()
    }
}
