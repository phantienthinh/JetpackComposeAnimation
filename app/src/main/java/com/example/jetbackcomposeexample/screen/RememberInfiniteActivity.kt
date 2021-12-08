package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class RememberInfiniteActivity:ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestRememberInfiniteTransition()
        }
    }

    @Composable
    fun TestRememberInfiniteTransition() {
        val infinityTransition = rememberInfiniteTransition()

        val color by infinityTransition.animateColor(
            initialValue = Color.Blue,
            targetValue = Color.Red,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        val colorRestart by infinityTransition.animateColor(
            initialValue = Color.DarkGray,
            targetValue = Color.Yellow,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color)
            ) {
                Button(
                    onClick = { /*TODO*/ }, modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                        .align(Alignment.Center)
                ) {
                    Text(text = "ReVerse", color = Color.White)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .background(colorRestart)
            ) {
                Button(
                    onClick = { /*TODO*/ }, modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                        .align(Alignment.Center)
                ) {
                    Text(text = "Restart", color = Color.White)
                }
            }
        }


    }
    @Preview
    @Composable
    fun show() {
        TestRememberInfiniteTransition()
    }
}