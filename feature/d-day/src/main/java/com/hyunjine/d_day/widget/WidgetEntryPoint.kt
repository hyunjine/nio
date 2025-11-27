package com.hyunjine.d_day.widget

import com.hyunjine.d_day.DDayRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    val repository: DDayRepository
}