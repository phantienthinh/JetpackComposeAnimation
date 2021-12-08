package com.example.jetbackcomposeexample.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

enum class StateAnimation {
    PAUSED, RUNNING
}

@ExperimentalAnimationGraphicsApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, VisibilityActivity::class.java)
                    startActivity(intent)
                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimatedVisibility")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, AnimationContentActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimatedContent")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, AnimateContentSizeActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimatedContentSize")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, CrossFadeActivity::class.java)
                    startActivity(intent)
                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "CrossFade")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, AnimationAsStateActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "animateAsState")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, AnimatableActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "Animatable")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, UpdateTransitionActivity::class.java)
                    startActivity(intent)
                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "UpdateTransition")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, RememberInfiniteActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "RememberInfiniteTransition")
            }
            Button(
                onClick = {

                    val intent = Intent(this@MainActivity, TargetBasedActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "TargetBasedAnimation")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, SpringActivity::class.java)
                    startActivity(intent)
                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimationSpec-Sping")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, EasingActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "Easing")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, CustomAnimationActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimationVector")
            }

            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, DecayAnimationActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "DecayAnimation")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, KeyFramesActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "KeyFrames")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, VectorDrawableActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "VectorDrawable")
            }
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, GestureActivity::class.java)
                    startActivity(intent)

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp, bottom = 50.dp)
            ) {
                Text(text = "Gesture")
            }
        }
    }


    fun Modifier.swipeToDismiss(
        onDismissed: () -> Unit
    ): Modifier = composed {
        val offsetX = remember { Animatable(0f) }
        pointerInput(Unit) {
            // Used to calculate fling decay.
            val decay = splineBasedDecay<Float>(this)
            // Use suspend functions for touch events and the Animatable.
            coroutineScope {
                while (true) {
                    // Detect a touch down event.
                    val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                    val velocityTracker = VelocityTracker()
                    // Stop any ongoing animation.
                    offsetX.stop()
                    awaitPointerEventScope {
                        horizontalDrag(pointerId) { change ->
                            // Update the animation value with touch events.
                            launch {
                                offsetX.snapTo(
                                    offsetX.value + change.positionChange().x
                                )
                            }
                            velocityTracker.addPosition(
                                change.uptimeMillis,
                                change.position
                            )
                        }
                    }
                    // No longer receiving touch events. Prepare the animation.
                    val velocity = velocityTracker.calculateVelocity().x
                    val targetOffsetX = decay.calculateTargetValue(
                        offsetX.value,
                        velocity
                    )
                    // The animation stops when it reaches the bounds.
                    offsetX.updateBounds(
                        lowerBound = -size.width.toFloat(),
                        upperBound = size.width.toFloat()
                    )
                    launch {
                        if (targetOffsetX.absoluteValue <= size.width) {
                            // Not enough velocity; Slide back.
                            offsetX.animateTo(
                                targetValue = 0f,
                                initialVelocity = velocity
                            )
                        } else {
                            // The element was swiped away.
                            offsetX.animateDecay(velocity, decay)
                            onDismissed()
                        }
                    }
                }
            }
        }
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
    }


}
