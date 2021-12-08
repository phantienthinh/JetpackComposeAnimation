package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetbackcomposeexample.R

class AnimationAsStateActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAnimateAsState()
        }
    }


    @Composable
    fun TestAnimateAsState() {
        var size by remember { mutableStateOf(100.dp) }
        val sizeAnim by animateDpAsState(
            targetValue = size,
            tween(durationMillis = 1000)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                val (bt1, bt2, img) = createRefs()
                Button(
                    onClick = { size += 50.dp },
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .constrainAs(bt1) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(bt2.start)
                        }
                ) {
                    Text(text = "up")
                }
                Button(
                    onClick = { size -= 50.dp },
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .constrainAs(bt2) {
                            top.linkTo(parent.top)
                            start.linkTo(bt1.end)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(text = "down")
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_mood),
                    contentDescription = "", modifier = Modifier
                        .size(size = sizeAnim)
                        .constrainAs(img) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    }


    @Preview
    @Composable
    fun show() {
        TestAnimateAsState()
    }
}