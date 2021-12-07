package com.example.jetbackcomposeexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@ExperimentalAnimationGraphicsApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutAnimation()
        }
    }

    @Composable
    fun LayoutAnimation() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
          /*  TestAnimatedVisibility()
            Spacer(modifier = Modifier.size(20.dp))
            TestAnimatedContent()
            Spacer(modifier = Modifier.size(20.dp))
            TestAnimateAsState()
            Spacer(modifier = Modifier.size(20.dp))
            TestAnimationContentSize()
            Spacer(modifier = Modifier.size(20.dp))
            TestCrossFade()
            Spacer(modifier = Modifier.size(20.dp))
            TestUpdateTransition()
            Spacer(modifier = Modifier.size(20.dp))
            TestAnimatable()
            Spacer(modifier = Modifier.size(20.dp))
            TestRememberInfiniteTransition()
            Spacer(modifier = Modifier.size(20.dp))
            TestSpring()
            Spacer(modifier = Modifier.size(20.dp))
            TestGesture()
            Spacer(modifier = Modifier.size(20.dp))
            TargetBasedAnimation()*/

            Spacer(modifier = Modifier.size(50.dp))
            AnimationKeyFrames()

        }
    }

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

    @Composable
    fun TestAnimatedContent() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            var count by remember { mutableStateOf(0) }
            val (bt, txt) = createRefs()
            Button(
                onClick = {
                    val rs = (0..1).random()
                    if (rs == 0)
                        count++
                    else {
                        count--
                    }
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .constrainAs(bt) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                Text("AnimationContent")
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

    @Composable
    fun TestAnimateAsState() {
        var colorState by remember { mutableStateOf(Color.Green) }
        val colorAnim by animateColorAsState(
            targetValue = colorState,
            tween(durationMillis = 5000)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorAnim)
                .height(200.dp)
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                val (bt1, bt2, img) = createRefs()
                Button(
                    onClick = { colorState = Color.Red },
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
                    onClick = { colorState = Color.Yellow },
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
                        .size(100.dp)
                        .constrainAs(img) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    }

    @Composable
    fun TestCrossFade() {
        var currentPage by remember { mutableStateOf("A") }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            val (bt, txt) = createRefs()
            Button(onClick = { currentPage = if (currentPage == "A") "B" else "A" },
                modifier = Modifier
                    .constrainAs(bt) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                Text("Change-Crossfade")
            }
            Crossfade(targetState = currentPage) { screen ->
                when (screen) {
                    "A" -> Text(
                        "Page A",
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .constrainAs(txt) {
                                top.linkTo(bt.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }, style = MaterialTheme.typography.h2
                    )
                    "B" -> Text(
                        "Page B",
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .constrainAs(txt) {
                                top.linkTo(bt.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }, style = MaterialTheme.typography.h4
                    )
                }
            }
        }

    }

    @Composable
    fun TestAnimatable() {
        var rs by remember { mutableStateOf(false) }
        val color = remember { Animatable(Color.Gray) }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
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
                color.animateTo(
                    if (rs) Color.Green else Color.Red,
                    animationSpec = tween(durationMillis = 1000)
                )
            }
            Box(
                Modifier
                    .size(100.dp)
                    .background(color.value)
                    .padding(top = 30.dp)
                    .constrainAs(box) {
                        top.linkTo(bt.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Text(
                    text = if (!rs) "red" else "Green",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        }

    }

    @Composable
    fun TestAnimationContentSize() {
        var expand by remember { mutableStateOf(false) }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
        ) {
            val (bt, box) = createRefs()
            Button(onClick = { expand = !expand },
                modifier = Modifier.constrainAs(bt) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Text("Expand-contentSize")
            }
            Text(
                text = stringResource(id = if (expand) R.string.lorem_ipsum else R.string.app_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .background(Color.Green)
                    .constrainAs(box) {
                        top.linkTo(bt.bottom)
                    }
            )
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
                Text(text = "update State", fontSize = (12.sp))
            }
        }


    }

    @Composable
    fun TestRememberInfiniteTransition() {
        val infinityTransition = rememberInfiniteTransition()

        val color by infinityTransition.animateColor(
            initialValue = Color.Blue,
            targetValue = Color.Red,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        val colorRestart by infinityTransition.animateColor(
            initialValue = Color.DarkGray,
            targetValue = Color.Yellow,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color)
            ) {
                Button(
                    onClick = { /*TODO*/ }, modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                        .align(Alignment.Center)
                ) {
                    Text(text = "ReVerse", color = Color.White)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .background(colorRestart)
            ) {
                Button(
                    onClick = { /*TODO*/ }, modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                        .align(Alignment.Center)
                ) {
                    Text(text = "Restart", color = Color.White)
                }
            }
        }


    }

    @Composable
    fun TestGesture() {
        val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .pointerInput(Unit) {
                    coroutineScope {
                        while (true) {
                            // Detect a tap event and obtain its position.
                            val position = awaitPointerEventScope {
                                awaitFirstDown().position
                            }
                            launch {
                                // Animate to the tap position.
                                offset.animateTo(position)
                            }
                        }
                    }
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_where_to_vote_24),
                contentDescription = "",
                modifier = Modifier
                    .size(48.dp)
                    .offset { offset.value.toIntOffset() }
            )
        }
    }

    private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

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


    @Composable
    fun TargetBasedAnimation() {
        val anim = remember {
            TargetBasedAnimation(
                animationSpec = tween(200),
                typeConverter = Float.VectorConverter,
                initialValue = 200f,
                targetValue = 1000f
            )
        }
        var playTime by remember { mutableStateOf(0L) }

        LaunchedEffect(anim) {
            val startTime = withFrameNanos { it }

            do {
                playTime = withFrameNanos { it } - startTime
                val animationValue = anim.getValueFromNanos(playTime)

            } while (true)
        }
    }

    @Composable
    fun AnimationKeyFrames() {

        var alpha by remember { mutableStateOf(0f) }
        val value by animateFloatAsState(
            targetValue = alpha
        , tween(durationMillis = 2000)
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = { alpha = 1f }) {

            }
            Icon(
                painter = painterResource(id = R.drawable.tw_icon),
                contentDescription = "",
                modifier = Modifier.alpha(value)
            )
        }


    }

    @Composable
    @Preview
    fun layoutTest() {
        LayoutAnimation()
    }

    enum class AppState {
        UP, DOWN
    }

}
