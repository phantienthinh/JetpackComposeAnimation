package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetbackcomposeexample.R

class UpdateTransitionActivity : ComponentActivity() {

    enum class AppState {
        UP, DOWN
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpdateTransitionLayout()
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @Composable
    fun UpdateTransitionLayout(){
        Column {
            TestUpdateTransition()
            ChangePhoneStyleAnimation()

        }
    }

    @Composable
    fun TestUpdateTransition() {
        var currentState by remember {
            mutableStateOf(AppState.UP)
        }
        val transition = updateTransition(targetState = currentState, label = "")

        val sizeAnim by transition.animateDp { state ->
            when (state) {
                AppState.UP -> 100.dp
                AppState.DOWN -> 50.dp
            }
        }
        val alpha by transition.animateFloat { state ->
            when (state) {
                AppState.UP -> 1f
                AppState.DOWN -> 0.5f
            }
        }
        val color by transition.animateColor { state ->
            when (state) {
                AppState.UP -> Color.Red
                AppState.DOWN -> Color.Blue
            }
        }
        val colorIcon by transition.animateColor { state ->
            when (state) {
                AppState.UP -> Color(0xffEC4786)
                AppState.DOWN -> Color(0xff000000)
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    currentState = if (currentState == AppState.UP) AppState.DOWN else AppState.UP
                },
                modifier = Modifier
                    .height(sizeAnim)
                    .width(sizeAnim * 2)
                    .alpha(alpha)
                    .background(color)
                    .align(Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check_icon),
                    contentDescription = "",
                    tint = colorIcon, modifier = Modifier.size(sizeAnim/2)
                )
            }
        }
    }

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @Composable
    fun ChangePhoneStyleAnimation(){
        var selected by remember { mutableStateOf(false) }
        val transition = updateTransition(selected)
        val borderColor by transition.animateColor { isSelected ->
            if (isSelected) Color.Magenta else Color.White
        }
        val elevation by transition.animateDp { isSelected ->
            if (isSelected) 10.dp else 2.dp
        }
        Surface(
            onClick = { selected = !selected },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, borderColor),
            elevation = elevation
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = "Hello, world!")
                transition.AnimatedVisibility(
                    visible = { targetSelected -> targetSelected },
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Text(text = "It is fine today.")
                }
                transition.AnimatedContent { targetState ->
                    if (targetState) {
                        Text(text = "Selected")
                    } else {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
                    }
                }
            }
        }
    }

    enum class BoxState { Collapsed, Expanded }

    @Composable
    fun AnimatingBox(boxState: BoxState) {
        val transitionData = updateTransitionData(boxState)
        // UI tree
        Box(
            modifier = Modifier
                .background(transitionData.color)
                .size(transitionData.size)
        )
    }

    // Holds the animation values.
    private class TransitionData(
        color: State<Color>,
        size: State<Dp>
    ) {
        val color by color
        val size by size
    }

    // Create a Transition and return its animation values.
    @Composable
    private fun updateTransitionData(boxState: BoxState): TransitionData {
        val transition = updateTransition(boxState)
        val color = transition.animateColor { state ->
            when (state) {
                BoxState.Collapsed -> Color.Gray
                BoxState.Expanded -> Color.Red
            }
        }
        val size = transition.animateDp { state ->
            when (state) {
                BoxState.Collapsed -> 64.dp
                BoxState.Expanded -> 128.dp
            }
        }
        return remember(transition) { TransitionData(color, size) }
    }

    @Preview
    @Composable
    fun show() {
        TestUpdateTransition()
    }
}