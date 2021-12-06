package com.example.jetbackcomposeexample.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(percent = 15),
    medium = RoundedCornerShape(4.dp),
    large = CutCornerShape(topStart = 16.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 16.dp)
)