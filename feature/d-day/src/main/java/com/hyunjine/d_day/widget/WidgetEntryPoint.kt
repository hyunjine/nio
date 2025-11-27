package com.hyunjine.d_day.widget

import android.content.Context
import com.hyunjine.d_day.DDayRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    val repository: DDayRepository
}

class GetRepository {
    private lateinit var _repository: DDayRepository

    operator fun invoke(context: Context): DDayRepository {
        if (!::_repository.isInitialized) {
            _repository = EntryPointAccessors.fromApplication(
                context.applicationContext,
                WidgetEntryPoint::class.java
            ).repository
        }
        return _repository
    }
}