package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

class EasingActivity :ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestEasing()
        }
    }

    @Composable
    fun TestEasing() {
        val CustomEasing = Easing { fraction ->
            fraction * fraction
        }
        var size by remember { mutableStateOf(50.dp) }
        val widthBt1 by animateDpAsState(
            targetValue = size,
            animationSpec = tween(durationMillis = 5000, easing = CustomEasing)
        )

        val widthBt2 by animateDpAsState(
            targetValue = size,
            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
        )
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (bt1, bt2, bt3) = createRefs()
            Button(onClick = { size += 300.dp }, modifier = Modifier
                .height(48.dp)
                .constrainAs(bt1) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Text(text = "Change width")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .width(widthBt1)
                .height(48.dp)
                .padding(top = 20.dp)
                .constrainAs(bt2) {
                    top.linkTo(bt1.bottom)
                    start.linkTo(parent.start)
                }) {

            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .width(widthBt2)
                .height(48.dp)
                .padding(top = 20.dp)
                .constrainAs(bt3) {
                    top.linkTo(bt2.bottom)
                    start.linkTo(parent.start)
                }) {

            }
        }
    }

    @Preview
    @Composable
    fun show() {
        TestEasing()
    }

}