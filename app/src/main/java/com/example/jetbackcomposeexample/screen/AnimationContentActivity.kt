package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@ExperimentalAnimationApi
class AnimationContentActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAnimatedContent()
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun TestAnimatedContent() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            var count by remember { mutableStateOf(0) }
            val (bt,bt2 ,txt) = createRefs()
            Button(
                onClick = {
                        count++
                },
                modifier = Modifier
                    .padding( 10.dp)
                    .constrainAs(bt) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(bt2.start)
                    }) {
                Text("Up")
            }
            Button(
                onClick = {
                        count--
                },
                modifier = Modifier
                    .padding( 10.dp)
                    .constrainAs(bt2) {
                        top.linkTo(parent.top)
                        start.linkTo(bt.end)
                        end.linkTo(parent.end)
                    }) {
                Text("Down")
            }
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInVertically({ height -> height }) + fadeIn() with
                                slideOutVertically({ height -> -height }) + fadeOut()
                    } else {
                        Log.d("ManhNQ", "AnimatedContent: else")
                        slideInVertically({ height -> -height }) + fadeIn() with
                                slideOutVertically({ height -> height }) + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }
            ) { targetCount ->
                Text(
                    text = "$targetCount",
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth()
                        .constrainAs(txt) {
                            top.linkTo(bt.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }, textAlign = TextAlign.Center, style = MaterialTheme.typography.h2
                )
            }
        }
    }

    @Preview
    @Composable
    fun show() {
        TestAnimatedContent()
    }
}