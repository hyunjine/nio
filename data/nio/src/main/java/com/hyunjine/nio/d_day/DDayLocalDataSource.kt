package com.hyunjine.nio.d_day

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DDayLocalDataSource {
    suspend fun getStartDate(): Flow<LocalDate>

    suspend fun updateStartDate(date: LocalDate)
}