package com.hyunjine.nio.lock

import com.hyunjine.lock.LockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import javax.inject.Inject

class LockRepositoryImpl @Inject constructor(): LockRepository {
    override fun getFocusModeFinishTime(): Flow<LocalDateTime?> {
        return flowOf(LocalDateTime.now().plusHours(1))
    }
}