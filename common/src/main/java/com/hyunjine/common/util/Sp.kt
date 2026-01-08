package com.hyunjine.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

val Int.spToDp: Dp
    @Composable get() = with(LocalDensity.current) {
        this@spToDp.sp.toDp()
    }