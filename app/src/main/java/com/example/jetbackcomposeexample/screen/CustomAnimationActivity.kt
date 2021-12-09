package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetbackcomposeexample.R
import com.example.jetbackcomposeexample.model.MySize

class CustomAnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestCustomAnimation()
        }
    }

    @Composable
    fun TestCustomAnimation() {
        var mySize by remember {
            mutableStateOf(MySize(10.dp, 10.dp))
        }
        val animSize by animateValueAsState<MySize, AnimationVector4D>(
            mySize,
            TwoWayConverter(
                convertToVector = { size: MySize ->
                    // Extract a float value from each of the `Dp` fields.
                    AnimationVector4D(
                        size.width.value,
                        size.height.value,
                        size.width.value,
                        size.height.value
                    )
                },
                convertFromVector = { vector: AnimationVector4D ->
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
    @Preview
    @Composable
    fun show() {
        TestCustomAnimation()
    }
}