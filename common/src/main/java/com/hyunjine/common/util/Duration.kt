package com.hyunjine.common.util

import kotlin.time.Duration

val Duration.minutes: Long
    get() = inWholeMinutes % 60

val Duration.seconds: Long
    get() = inWholeSeconds % 60