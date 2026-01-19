package com.hyunjine.common.extension

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

fun getLoremIpsum(size: Int = 1_000): String = LoremIpsum(size).values.joinToString("")