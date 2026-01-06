package com.hyunjine.common.ui.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class NioTypography(

    // Display
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,

    // Headline
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,

    // Title
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,

    // Body
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,

    // Label
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle
) {

    /* ---------- Display ---------- */

    val displayLargeEmphasized: TextStyle
        get() = displayLarge.copy(fontWeight = FontWeight.Medium)

    val displayMediumEmphasized: TextStyle
        get() = displayMedium.copy(fontWeight = FontWeight.Medium)

    val displaySmallEmphasized: TextStyle
        get() = displaySmall.copy(fontWeight = FontWeight.Medium)

    /* ---------- Headline ---------- */

    val headlineLargeEmphasized: TextStyle
        get() = headlineLarge.copy(fontWeight = FontWeight.Medium)

    val headlineMediumEmphasized: TextStyle
        get() = headlineMedium.copy(fontWeight = FontWeight.Medium)

    val headlineSmallEmphasized: TextStyle
        get() = headlineSmall.copy(fontWeight = FontWeight.Medium)

    /* ---------- Title ---------- */

    val titleLargeEmphasized: TextStyle
        get() = titleLarge.copy(fontWeight = FontWeight.Medium)

    val titleMediumEmphasized: TextStyle
        get() = titleMedium.copy(fontWeight = FontWeight.Bold)

    val titleSmallEmphasized: TextStyle
        get() = titleSmall.copy(fontWeight = FontWeight.Bold)

    /* ---------- Body ---------- */

    val bodyLargeEmphasized: TextStyle
        get() = bodyLarge.copy(fontWeight = FontWeight.Medium)

    val bodyMediumEmphasized: TextStyle
        get() = bodyMedium.copy(fontWeight = FontWeight.Medium)

    val bodySmallEmphasized: TextStyle
        get() = bodySmall.copy(fontWeight = FontWeight.Medium)

    /* ---------- Label ---------- */

    val labelLargeEmphasized: TextStyle
        get() = labelLarge.copy(fontWeight = FontWeight.Bold)

    val labelMediumEmphasized: TextStyle
        get() = labelMedium.copy(fontWeight = FontWeight.Bold)

    val labelSmallEmphasized: TextStyle
        get() = labelSmall.copy(fontWeight = FontWeight.Bold)
}


val NioTypographyInstance = NioTypography(

    // Display
    displayLarge = BaseTextStyle.copy(
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        fontWeight = FontWeight.Normal
    ),
    displayMedium = BaseTextStyle.copy(
        fontSize = 45.sp,
        lineHeight = 52.sp,
        fontWeight = FontWeight.Normal
    ),
    displaySmall = BaseTextStyle.copy(
        fontSize = 36.sp,
        lineHeight = 44.sp,
        fontWeight = FontWeight.Normal
    ),

    // Headline
    headlineLarge = BaseTextStyle.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Normal
    ),
    headlineMedium = BaseTextStyle.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Normal
    ),
    headlineSmall = BaseTextStyle.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Normal
    ),

    // Title
    titleLarge = BaseTextStyle.copy(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Normal
    ),
    titleMedium = BaseTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.Medium
    ),
    titleSmall = BaseTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium
    ),

    // Body
    bodyLarge = BaseTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = BaseTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = BaseTextStyle.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        fontWeight = FontWeight.Normal
    ),

    // Label
    labelLarge = BaseTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = BaseTextStyle.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    ),
    labelSmall = BaseTextStyle.copy(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    )
)

val typography: NioTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalAppTypography.current

val LocalAppTypography = staticCompositionLocalOf<NioTypography> {
    error("AppTypography not provided")
}