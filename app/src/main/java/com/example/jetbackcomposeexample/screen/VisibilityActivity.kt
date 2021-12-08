package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetbackcomposeexample.R

@ExperimentalAnimationApi
class VisibilityActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAnimatedVisibility()
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun TestAnimatedVisibility() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            var isVisible by remember { mutableStateOf(true) }
            var content by remember { mutableStateOf("Show") }
            Button(
                onClick = {
                    isVisible = !isVisible
                    content = if (content == "Show") "Hide" else "Show"
                },
                modifier = Modifier
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Crossfade(targetState = content) {
                    when (it) {
                        "Show" -> {
                            Text(content)
                        }
                        "Hide" -> {
                            Text(content)
                        }
                    }

                }
            }
            HomeFloatingActionButton(isVisible, this@VisibilityActivity::showNoti)
            AnimatedVisibility(
                visible = isVisible,
                exit = slideOutHorizontally() + fadeOut(),
                enter = slideInHorizontally() + fadeIn(),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.tw_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp)
                )
            }

        }
    }

    fun showNoti() {
        Toast.makeText(this, "Show or Hide", Toast.LENGTH_SHORT).show()
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun HomeFloatingActionButton(
        extended: Boolean,
        onClick: () -> Unit
    ) {
        FloatingActionButton(onClick = onClick) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
                AnimatedVisibility(visible = extended) {
                    Text(
                        text = "Edit",
                        modifier = Modifier
                            .padding(start = 8.dp, top = 3.dp)
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun show() {
        TestAnimatedVisibility()
    }
}