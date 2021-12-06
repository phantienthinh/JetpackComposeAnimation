package com.example.jetbackcomposeexample.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

enum class AppColorType {
    BLUE, GREEN
}

@Stable
data class AppColors(
    val headerColor: Color,
    val appbarColor: Color,
    val topBarColor: Color,
    val backgroundColor: Color
)

private object AppCustomColors {
    val blueColor1 = Color(0xFF4CAF50)
    val blueColor2 = Color(0x5A33E03A)
    val blueColor3 = Color(0xFF005A04)
    val greenColor1 = Color(0xFF2196F3)
    val greenColor2 = Color(0xFF2155F3)
    val greenColor3 = Color(0xFF4703F4)
}

private val blueDarkColors = AppColors(
    headerColor = AppCustomColors.blueColor1,
    appbarColor = AppCustomColors.blueColor2,
    topBarColor = AppCustomColors.blueColor3,
    backgroundColor = AppCustomColors.blueColor2
)
private val blueLightColors = AppColors(
    headerColor = AppCustomColors.blueColor1,
    appbarColor = AppCustomColors.blueColor2,
    topBarColor = AppCustomColors.blueColor3,
    backgroundColor = AppCustomColors.blueColor2

)
private val greenLightColors = AppColors(
    headerColor = AppCustomColors.greenColor1,
    appbarColor = AppCustomColors.greenColor2,
    topBarColor = AppCustomColors.greenColor3,
    backgroundColor = AppCustomColors.greenColor1
)
private val greenDarkColors = AppColors(
    headerColor = AppCustomColors.greenColor1,
    appbarColor = AppCustomColors.greenColor2,
    topBarColor = AppCustomColors.greenColor3,
    backgroundColor = AppCustomColors.greenColor1
)

@Composable
fun appColors(type: AppColorType, darkTheme: Boolean): AppColors =
    when (type) {
        AppColorType.BLUE -> if (darkTheme) blueDarkColors else blueLightColors
        AppColorType.GREEN -> if (darkTheme) greenDarkColors else greenLightColors
    }

private val LocalAppColors = staticCompositionLocalOf {
    blueDarkColors
}

object AppTheme {
    val colorNews: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorType: AppColorType = AppColorType.BLUE,
    content: @Composable () -> Unit,
) {
    val colors = appColors(type = colorType, darkTheme = darkTheme)
    CompositionLocalProvider(
        LocalAppColors provides colors
    ) {
        content()
    }
}