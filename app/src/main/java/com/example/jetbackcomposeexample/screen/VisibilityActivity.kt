package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@ExperimentalAnimationApi
class VisibilityActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAnimatedVisibility()
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun TestAnimatedVisibility() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            var isVisible by remember { mutableStateOf(true) }
            val (bt, txt) = createRefs()
            Button(
                onClick = {
                    isVisible = !isVisible
                },
                modifier = Modifier
                    .padding(top = 50.dp)
                    .constrainAs(bt) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                Text("TestAnimationVisibility")
            }
            AnimatedVisibility(
                visible = isVisible,
                exit = fadeOut(),
                enter = fadeIn()
            ) {
                Text(
                    text = "Hello", modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(txt) {
                            top.linkTo(bt.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }, fontSize = 30.sp, textAlign = TextAlign.Center
                )
            }
        }
    }
}