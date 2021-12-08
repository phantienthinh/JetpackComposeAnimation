package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.jetbackcomposeexample.R

class KeyFramesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationKeyFrames()
        }
    }


    @Composable
    fun AnimationKeyFrames() {

        var alpha by remember { mutableStateOf(0f) }
        val value by animateFloatAsState(
            targetValue = alpha,
            animationSpec = keyframes {
                durationMillis = 2000
                0.2f at 0
                0.2f at 750
                0.4f at 1500
                0.8f at 1750
                1f at 2000
            }
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = { alpha = 1f }, modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 30.dp)) {
                Text(text = "Show")
            }
            Image(
                painter = rememberImagePainter("https://cdn.hswstatic.com/gif/maps.jpg"),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(value)
            )
        }
    }

    @Preview
    @Composable
    fun show() {
        AnimationKeyFrames()
    }
}