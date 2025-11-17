package com.hyunjine.lock

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface LockRepository {
    /**
     * 집중 모드가 끝나는 시간을 가져옵니다.
     * null이면 집중 모드가 비활성화라 판단합니다.
     */
    fun getFocusModeFinishTime(): Flow<LocalDateTime?>
}