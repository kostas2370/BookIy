package com.example.bookindexer.screens

import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bookindexer.R


@Composable
fun UserRatingBar(
    // 1. Parameters for UserRatingBar
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    is_Locked: Boolean = false,
    ratingState: MutableState<Int> = remember { mutableIntStateOf(0) },
    ratingIconPainter: Painter = painterResource(id = R.drawable.ic_star),
    selectedColor: Color = Color(0xFFFFD700),
    unselectedColor: Color = Color(0xFFA2ADB1)
) {
    Row(modifier = modifier.wrapContentSize()) {
        // 2. Star Icon Generation Loop
        for (value in 1..5) {
            StarIcon(
                size = size,
                painter = ratingIconPainter,
                ratingValue = value,
                ratingState = ratingState,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                is_Locked = is_Locked
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StarIcon(
    // 3. Parameters for StarIcon
    size: Dp,
    ratingState: MutableState<Int>,
    painter: Painter,
    ratingValue: Int,
    selectedColor: Color,
    unselectedColor: Color,
    is_Locked: Boolean = false
) {
    // 4. Color Animation
    val tint by animateColorAsState(
        targetValue = if (ratingValue <= ratingState.value) selectedColor else unselectedColor,
        label = ""
    )

    Icon(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(size)
            // 5. Touch Interaction Handling
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (!is_Locked){
                            ratingState.value = ratingValue

                        }
                    }
                }
                true
            },
        tint = tint
    )
}