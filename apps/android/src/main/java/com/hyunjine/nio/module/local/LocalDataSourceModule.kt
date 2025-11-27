package com.hyunjine.nio.module.local

import com.hyunjine.nio.d_day.DDayLocalDataSource
import com.hyunjine.nio.d_day.DDayLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LocalDataSourceModule {
    @Singleton
    @Binds
    abstract fun bindDDayLocalDataSource(supabaseClient: DDayLocalDataSourceImpl): DDayLocalDataSource
}