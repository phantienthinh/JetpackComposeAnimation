package com.example.jetbackcomposeexample.ui.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
object CustomRipple : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Blue

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        pressedAlpha = 0.2f,
        focusedAlpha = 0.5f,
        draggedAlpha = 0.15f,
        hoveredAlpha = 0.05f
    )

}