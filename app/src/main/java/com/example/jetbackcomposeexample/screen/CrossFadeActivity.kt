package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.jetbackcomposeexample.R

class CrossFadeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestCrossFade()
        }
    }

    @Composable
    fun TestCrossFade() {
        var currentPage by remember { mutableStateOf("A") }
            Column(
                modifier = Modifier
                    .background(Color.Blue)

            ) {
                Button(onClick = { currentPage = if (currentPage == "A") "B" else "A" },
                ) {
                    Text("Change-Crossfade")
                }
                Crossfade(
                    targetState = currentPage,
                    modifier = Modifier.animateContentSize(),
                    animationSpec = tween(durationMillis = 1000)
                ) { screen ->
                    when (screen) {
                        "A" -> Image(
                            painter = painterResource(id = R.drawable.tw_icon),   contentDescription = "",
                            modifier = Modifier
                                .padding(top = 60.dp)
                                .height(200.dp)
                        )
                        "B" -> Text(
                            "Good bye",
                            modifier = Modifier
                                .padding(top = 80.dp)
                                .height(50.dp), style = MaterialTheme.typography.h4, textAlign = TextAlign.Center
                        )
                    }
                }
                Button(onClick = { currentPage = if (currentPage == "A") "B" else "A" },
                ) {
                    Text("Change-Crossfade")
                }
            }
    }

    @Preview(backgroundColor = 0xffffff, widthDp = 320, heightDp = 640)
    @Composable
    fun show() {
        Surface(color = Color.White, modifier = Modifier.background(Color.White)) {
            TestCrossFade()
        }
    }


}