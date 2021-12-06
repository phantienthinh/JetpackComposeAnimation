package com.example.jetbackcomposeexample.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.GenericFontFamily
import androidx.compose.ui.unit.sp
import com.example.jetbackcomposeexample.R

private val Montserrat = FontFamily(Font(R.font.bold))

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    button = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)