package com.hyunjine.nio.d_day

import com.hyunjine.d_day.DDayRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class DDayRepositoryImpl @Inject constructor(
    private val dDayLocalDataSource: DDayLocalDataSource
): DDayRepository, DDayLocalDataSource by dDayLocalDataSource {

}