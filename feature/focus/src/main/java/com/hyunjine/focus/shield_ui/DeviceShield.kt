package com.hyunjine.focus.shield_ui

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class DeviceShield(
    private val context: Context
) {
    private val windowManager: WindowManager by lazy {
        context.getSystemService(WINDOW_SERVICE) as WindowManager
    }
    private val lifecycleOwner: ServiceLifecycleOwner by lazy {
        ServiceLifecycleOwner()
    }
    private val composeView: ComposeView by lazy {
        ComposeView(context).apply {
            setViewTreeLifecycleOwner(lifecycleOwner)
            setViewTreeSavedStateRegistryOwner(lifecycleOwner)

            setContent {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Red),
                    text = "하이"
                )
            }
        }
    }
    private val layoutParams: WindowManager.LayoutParams by lazy {
        WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        )
    }

    fun show() {
        lifecycleOwner.onResume()
        if (!composeView.hasParent()) {
            windowManager.addView(composeView, layoutParams)
        }
    }

    fun hide() {
        lifecycleOwner.onStop()
        if (composeView.hasParent()) {
            windowManager.removeView(composeView)
        }
    }

    private fun ComposeView.hasParent(): Boolean {
        val root = rootView as ViewGroup
        return root.parent != null
    }
}

