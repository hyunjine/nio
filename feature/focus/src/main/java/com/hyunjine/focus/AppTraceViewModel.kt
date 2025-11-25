package com.hyunjine.focus

import com.hyunjine.common.log.wlog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

class AppTraceViewModel(
    coroutineScope: CoroutineScope,
    repository: LockRepository
) {
    companion object {
        private val LOCK_APP_PACKAGES: Set<String> = setOf(
            "com.instagram.android", // 인스타
            "com.google.android.youtube", // 유튜브
            "com.sec.android.app.sbrowser" // 삼성 인터넷
        )
    }

    private val activatedPackageName: MutableStateFlow<CharSequence?> = MutableStateFlow("com.hyunjine.nio")

    /**
     * 오버레이 화면 여부를 나타내는 flow입니다.
     * true면 활성화, false면 비활성화 입니다.
     */
    val screenLockState: StateFlow<Boolean> = activatedPackageName.combine(repository.getFocusModeFinishTime()) { packageName, finishTime ->
        // 포그라운드 앱이 LOCK_APP_PACKAGES에 포함되어 있고, finishTime이 현재시간보다 이후이면 활성화 시킵니다.
        wlog(packageName)
        finishTime != null && LOCK_APP_PACKAGES.contains(packageName) && finishTime.isAfter(LocalDateTime.now())
    }.stateIn(scope = coroutineScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = false)


    /**
     * 현재 포그라운드에 있는 앱의 패키지명이 변경되면 호출합니다.
     * @param packageName 현재 포그라운드에 있는 앱의 패키지명 입니다.
     */
    fun changeWindowsContent(packageName: CharSequence?) {
        activatedPackageName.value = packageName
    }
}