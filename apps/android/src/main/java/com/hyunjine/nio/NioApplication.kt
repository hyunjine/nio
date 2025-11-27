package com.hyunjine.nio

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import com.hyunjine.common.log.startLog
import com.hyunjine.d_day.screen_receiver.ScreenOnReceiver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NioApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startLog()
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        registerReceiver(ScreenOnReceiver(), filter)
    }
}