package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth().animateContentSize()
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
                    "A" -> Image(
                        painter = rememberImagePainter("https://thumbs.dreamstime.com/b/vector-background-end-black-background-clapperboard-end-film-screensaver-movie-black-background-157772649.jpg"),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .fillMaxWidth()
                            .height(200.dp)
                            .constrainAs(txt) {
                                top.linkTo(bt.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                    "B" -> Text(
                        "Good bye",
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .fillMaxSize()
                            .constrainAs(txt) {
                                top.linkTo(bt.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }, style = MaterialTheme.typography.h4, textAlign = TextAlign.Center
                    )
                }
            }
        }

    }

    @Preview
    @Composable
    fun show() {
        TestCrossFade()
    }


}