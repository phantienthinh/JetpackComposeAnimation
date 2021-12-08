package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetbackcomposeexample.ANIMATED_VISIBILITY
import com.example.jetbackcomposeexample.R
import com.example.jetbackcomposeexample.model.MySize
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
        val navController = rememberNavController()
        NavHost(navController, startDestination = "Main") {
            composable("Main") { MainScreen() }
        }
        Column(Modifier.fillMaxSize()) {
            Button(
                onClick = { }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimatedVisibility")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimatedContent")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimatedContentSize")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "CrossFade")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "animateAsState")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "Animatable")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "UpdateTransition")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "RememberInfiniteTransition")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "TargetBasedAnimation")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimationSpec")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "Easing")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "AnimationVector")
            }
            Button(
                onClick = {  }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "VectorDrawable")
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
            Crossfade(
                targetState = currentPage,
                animationSpec = tween(durationMillis = 1000)
            ) { screen ->
                when (screen) {
                    "A" -> Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_toys_24),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .size(120.dp)
                            .constrainAs(txt) {
                                top.linkTo(bt.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
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
        val scaleAnim = remember { Animatable(1f) }

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
                    .size(100.dp)
                    .scale(scaleAnim.value)
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
        val targetBasedAnimation = remember {
            TargetBasedAnimation(
                animationSpec = tween(2000),
                typeConverter = Float.VectorConverter,
                initialValue = 0f,
                targetValue = 1000f
            )
        }

        var playTime = remember { 0L }
        val animationScope = rememberCoroutineScope()

        var StateAnim by remember { mutableStateOf(StateAnimation.PAUSED) }
        var animationValue by remember { mutableStateOf(0f) }

        val onClick: () -> Unit = {
            if (animationValue == 1000f) {
                animationValue = 0f
                playTime = 0L
            }

            StateAnim = when (StateAnim) {
                StateAnimation.RUNNING -> StateAnimation.PAUSED
                StateAnimation.PAUSED -> StateAnimation.RUNNING
            }

            animationScope.launch {
                val startTime = withFrameNanos { it } - playTime

                while (StateAnim == StateAnimation.RUNNING) {
                    playTime = withFrameNanos { it } - startTime

                    animationValue = targetBasedAnimation.getValueFromNanos(playTime)
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { onClick.invoke() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Run")
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                drawCircle(
                    color = Color.White,
                    radius = 40f,
                    center = Offset(
                        x = 300f,
                        y = animationValue
                    )
                )
                Log.d("ManhNQ", "TargetBasedAnimation: $animationValue")
            }
        }

    }

    @Composable
    fun TestDecayAnimation() {
        val decayAnimation = remember {
            DecayAnimation(
                animationSpec = FloatExponentialDecaySpec(
                    // How quick the animation will stop
                    frictionMultiplier = 0.3f
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
                )
            )
        }

    }

    @Composable
    fun AnimationKeyFrames() {

        var alpha by remember { mutableStateOf(0f) }
        val value by animateFloatAsState(
            targetValue = alpha,
            animationSpec = keyframes {
                durationMillis = 2000
                0.2f at 0
                0.2f at 750
                0.4f at 1500
                0.8f at 1750
                1f at 2000
            }
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
    fun TestEasing() {
        val CustomEasing = Easing { fraction ->
            fraction * fraction
        }
        var size by remember { mutableStateOf(50.dp) }
        val widthBt1 by animateDpAsState(
            targetValue = size,
            animationSpec = tween(durationMillis = 5000, easing = CustomEasing)
        )

        val widthBt2 by animateDpAsState(
            targetValue = size,
            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
        )
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (bt1, bt2, bt3) = createRefs()
            Button(onClick = { size += 300.dp }, modifier = Modifier
                .height(48.dp)
                .constrainAs(bt1) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Text(text = "Change width")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .width(widthBt1)
                .height(48.dp)
                .padding(top = 20.dp)
                .constrainAs(bt2) {
                    top.linkTo(bt1.bottom)
                    start.linkTo(parent.start)
                }) {

            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .width(widthBt2)
                .height(48.dp)
                .padding(top = 20.dp)
                .constrainAs(bt3) {
                    top.linkTo(bt2.bottom)
                    start.linkTo(parent.start)
                }) {

            }
        }
    }

    @Composable
    fun TestCustomAnimation() {
        var mySize by remember {
            mutableStateOf(MySize(10.dp, 10.dp))
        }
        val animSize by animateValueAsState<MySize, AnimationVector2D>(
            mySize,
            TwoWayConverter(
                convertToVector = { size: MySize ->
                    // Extract a float value from each of the `Dp` fields.
                    AnimationVector2D(size.width.value, size.height.value)
                },
                convertFromVector = { vector: AnimationVector2D ->
                    MySize(vector.v1.dp, vector.v2.dp)
                }
            )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(id = R.drawable.ic_car), contentDescription = "",
                modifier = Modifier
                    .size(animSize.width, animSize.height)
                    .background(Color.Red)
            )

            Button(onClick = {
                mySize =
                    if (mySize.height == 10.dp) MySize(100.dp, 100.dp) else MySize(10.dp, 10.dp)
            }) {

            }
        }
    }

    enum class AppState {
        UP, DOWN
    }


}
