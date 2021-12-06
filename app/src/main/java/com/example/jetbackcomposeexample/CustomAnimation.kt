package com.example.jetbackcomposeexample

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp

private enum class BoxState {
    Collapsed,
    Expanded
}

@Composable
fun AnimationCustom() {
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }
    val transition = updateTransition(currentState, label = "")


    val alpha by transition.animateFloat { state ->
        when (state) {
            BoxState.Collapsed -> 1f
            BoxState.Expanded -> 0.5f
        }
    }

    val sizeNew by transition.animateInt { size ->
        when (size) {
            BoxState.Collapsed -> 250
            BoxState.Expanded -> 150
        }
    }


    Button(
        onClick = {
            currentState =
                if (currentState == BoxState.Expanded) BoxState.Collapsed else BoxState.Expanded
        }, modifier = Modifier
            .padding(top = 100.dp, start = 100.dp)
            .width(sizeNew.dp)
            .alpha(alpha)
    ) {
        Text(text = "Click")
    }
}