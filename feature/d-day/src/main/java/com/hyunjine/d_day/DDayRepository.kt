package com.hyunjine.d_day

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DDayRepository {
    suspend fun getStartDate(): Flow<LocalDate>

    suspend fun updateStartDate(date: LocalDate)
}