package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter

class AnimatableActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAnimatable()
        }
    }

    @Composable
    fun TestAnimatable() {
        var rs by remember { mutableStateOf(false) }
        val scaleAnim = remember { Animatable(1f) }
        val color = remember { androidx.compose.animation.Animatable(Color.Red) }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (bt, box) = createRefs()
            Button(onClick = { rs = !rs },
                modifier = Modifier.constrainAs(bt) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Text("AnimateTable")
            }

            LaunchedEffect(rs) {
                color.animateTo(if (rs) Color.Blue else Color.Yellow)
                color.animateTo(if (rs) Color.Yellow else Color.Blue)
                color.animateTo(if (rs) Color.Blue else Color.Yellow)
                if (rs) {
                    scaleAnim.animateDecay(
                        1f,
                        exponentialDecay(
                            frictionMultiplier = 0.1f,
                            absVelocityThreshold = 0.1f
                        )
                    )
                }
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp)
                    .constrainAs(box) {
                        top.linkTo(bt.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Image(
                    painter = rememberImagePainter("https://www.shareicon.net/data/512x512/2016/01/14/703158_warning_512x512.png"),
                    contentDescription = "",
                    modifier = Modifier.size(48.dp).scale(scaleAnim.value).align(Alignment.TopCenter)
                )
            }
        }
    }

    @Preview
    @Composable
    fun show() {
        TestAnimatable()
    }
}