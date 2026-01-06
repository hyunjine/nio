package com.hyunjine.common.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.hyunjine.common.ui.theme.typography.LocalAppTypography
import com.hyunjine.common.ui.theme.typography.NioTypography
import com.hyunjine.common.ui.theme.typography.NioTypographyInstance

@Composable
fun NioTheme(
    typography: NioTypography = NioTypographyInstance,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    CompositionLocalProvider(
        LocalAppTypography provides typography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}