package com.example.jetbackcomposeexample.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetbackcomposeexample.R

class VectorDrawableActivity : ComponentActivity() {

    @ExperimentalAnimationGraphicsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAnimationDrawable()
        }
    }


    @ExperimentalAnimationGraphicsApi
    @Composable
    fun TestAnimationDrawable() {
        var expand by remember { mutableStateOf(true) }
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (bt, img) = createRefs()
            Button(onClick = { expand = !expand }, modifier = Modifier.constrainAs(bt) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                Text(text = "Expand")
            }
            val vectorAnim = animatedVectorResource(id = R.drawable.avd_anim_expand_to_narrow)
            Image(
                painter = vectorAnim.painterFor(atEnd = expand),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .constrainAs(img) {
                        top.linkTo(bt.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }
    }

    @ExperimentalAnimationGraphicsApi
    @Preview
    @Composable
    fun show() {
        TestAnimationDrawable()
    }
}