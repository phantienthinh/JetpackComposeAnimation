package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetbackcomposeexample.R

class SpringActivity:ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestSpring()
        }
    }

    @Composable
    fun TestSpring() {
        var isLarge by remember { mutableStateOf(true) }
        var numClick by remember { mutableStateOf(1) }

        val value1 by animateDpAsState(
            targetValue = if (isLarge) 200.dp else 50.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
        val value2 by animateDpAsState(
            targetValue = if (isLarge) 200.dp else 50.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
        val value3 by animateDpAsState(
            targetValue = if (isLarge) 200.dp else 50.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
        ) {
            val (bt1, bt2, bt3, img) = createRefs()
            Button(onClick = {
                numClick = 1
                isLarge = !isLarge
            },
                modifier = Modifier.constrainAs(bt1) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(bt2.start)
                }) {
                Text("Toggle1")
            }
            Button(onClick = {
                numClick = 2
                isLarge = !isLarge
            },
                modifier = Modifier.constrainAs(bt2) {
                    top.linkTo(parent.top)
                    start.linkTo(bt1.end)
                    end.linkTo(bt3.start)
                }) {
                Text("Toggle2")
            }
            Button(onClick = {
                numClick = 3
                isLarge = !isLarge
            },
                modifier = Modifier.constrainAs(bt3) {
                    top.linkTo(parent.top)
                    start.linkTo(bt2.end)
                    end.linkTo(parent.end)
                }) {
                Text("Toggle3")
            }

            Icon(
                painter = painterResource(id = R.drawable.tw_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(
                        when (numClick) {
                            1 -> value1
                            2 -> value2
                            else -> value3
                        }
                    )
                    .constrainAs(img) {
                        top.linkTo(bt1.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }
    }

    @Preview
    @Composable
    fun show() {
        TestSpring()
    }
}