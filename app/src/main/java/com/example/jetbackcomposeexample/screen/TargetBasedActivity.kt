package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

class TargetBasedActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TargetBasedAnimation()
        }
    }

    @Composable
    fun TargetBasedAnimation() {
        val targetBasedAnimation = remember {
            androidx.compose.animation.core.TargetBasedAnimation(
                animationSpec = tween(2000),
                typeConverter = Float.VectorConverter,
                initialValue = 0f,
                targetValue = 1000f
            )
        }

        var playTime = remember { 0L }
        val animationScope = rememberCoroutineScope()

        var StateAnim by remember { mutableStateOf(StateAnimation.PAUSED) }
        var animationValue by remember { mutableStateOf(0f) }

        val onClick: () -> Unit = {
            if (animationValue == 1000f) {
                animationValue = 0f
                playTime = 0L
            }

            StateAnim = when (StateAnim) {
                StateAnimation.RUNNING -> StateAnimation.PAUSED
                StateAnimation.PAUSED -> StateAnimation.RUNNING
            }

            animationScope.launch {
                val startTime = withFrameNanos { it } - playTime

                while (StateAnim == StateAnimation.RUNNING) {
                    playTime = withFrameNanos { it } - startTime

                    animationValue = targetBasedAnimation.getValueFromNanos(playTime)
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { onClick.invoke() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Run")
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                drawCircle(
                    color = Color.White,
                    radius = 40f,
                    center = Offset(
                        x = 300f,
                        y = animationValue
                    )
                )
                Log.d("ManhNQ", "TargetBasedAnimation: $animationValue")
            }
        }

    }

    @Preview
    @Composable
    fun show() {
        TargetBasedAnimation()
    }
}