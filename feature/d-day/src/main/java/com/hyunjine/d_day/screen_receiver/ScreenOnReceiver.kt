package com.hyunjine.d_day.screen_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.updateAll
import com.hyunjine.d_day.widget.CounterWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScreenOnReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_ON) {
            val job = CoroutineScope(Dispatchers.Main).launch {
                CounterWidget().updateAll(context)
            }
            job.invokeOnCompletion {
                job.cancel()
            }
        }
    }
}