package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetbackcomposeexample.R

class UpdateTransitionActivity : ComponentActivity() {

    enum class AppState {
        UP, DOWN
    }

    @ExperimentalTransitionApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestUpdateTransition()
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalTransitionApi
    @Composable
    fun TestUpdateTransition() {
        var currentState by remember {
            mutableStateOf(AppState.UP)
        }

//        val currentState = remember {
//            MutableTransitionState(AppState.UP)
//        }
//        currentState.targetState = AppState.DOWN

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
                    tint = colorIcon, modifier = Modifier.size(sizeAnim / 2)
                )
                UpView(transition.createChildTransition {
                    it == AppState.UP
                })
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun UpView(isUp: Transition<Boolean>) {
        AnimatedVisibility(visible = isUp.currentState) {
            Button(
                onClick = { },
                modifier = Modifier.size(100.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {

            }
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalTransitionApi
    @Preview
    @Composable
    fun show() {
        TestUpdateTransition()
    }
}