package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.DecayAnimation
import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetbackcomposeexample.R
import kotlinx.coroutines.launch

class DecayAnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestDecayAnimation()
        }
    }

    @Composable
    fun TestDecayAnimation() {
        val decayAnimation = remember {
            DecayAnimation(
                animationSpec = FloatExponentialDecaySpec(
                    // How quick the animation will stop
                    frictionMultiplier = 0.5f
                ),
                initialValue = 0f,
                initialVelocity = 600f
            )
        }

        // We will manually handle the play time of the animation
        var playTime by remember { mutableStateOf(0L) }

        val animationScope = rememberCoroutineScope()
        var StateAnim by remember { mutableStateOf(StateAnimation.PAUSED) }
        var animationValue by remember { mutableStateOf(0f) }

        val onClick: () -> Unit = {
            StateAnim = when (StateAnim) {
                StateAnimation.RUNNING -> StateAnimation.PAUSED
                StateAnimation.PAUSED -> StateAnimation.RUNNING
            }

            animationScope.launch {

                var startTime = withFrameNanos { it } - playTime

                while (StateAnim == StateAnimation.RUNNING) {

                    if (decayAnimation.isFinishedFromNanos(playTime)) startTime =
                        withFrameNanos { it }

                    playTime = withFrameNanos { it } - startTime
                    animationValue = decayAnimation.getValueFromNanos(playTime)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Button(onClick = { onClick.invoke() }) {
                Text(text = "RUN")
            }
            Image(
                painter = painterResource(id = R.drawable.ic_car),
                contentDescription = "",
                modifier = Modifier.offset(
                    x = animationValue.dp,
                    y = 250.dp
                ), colorFilter = ColorFilter.tint(Color.Black)
            )
        }

    }

    @Preview
    @Composable
    fun show() {
        TestDecayAnimation()
    }

}