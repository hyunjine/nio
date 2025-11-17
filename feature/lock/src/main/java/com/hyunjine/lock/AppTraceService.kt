package com.hyunjine.lock


import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.hyunjine.lock.shield_ui.DeviceShield
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class AppTraceService : AccessibilityService(), CoroutineScope {
    @Inject
    lateinit var repository: LockRepository

    private val viewModel: AppTraceViewModel by lazy {
        AppTraceViewModel(coroutineScope = this, repository = repository)
    }

    private val deviceShield: DeviceShield by lazy {
        DeviceShield(this)
    }

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate() {
        super.onCreate()
        launch {
            viewModel.screenLockState.collectLatest { isShow ->
                if (isShow) {
                    deviceShield.show()
                } else {
                    deviceShield.hide()
                }
            }
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        serviceInfo = serviceInfo.apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        viewModel.changeWindowsContent(event.packageName)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onInterrupt() {

    }

}