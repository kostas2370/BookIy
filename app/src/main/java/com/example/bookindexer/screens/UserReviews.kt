package com.example.bookindexer.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookindexer.Model.BookReviewViewModel

import com.example.bookindexer.data.BookReviewResponse

@Composable
fun BookUserReview(
    reviews: BookReviewResponse

) {


    Column (horizontalAlignment = Alignment.Start,modifier = Modifier
        .fillMaxWidth()
        .height(860.dp)
        .padding(top = 320.dp, start = 10.dp, end= 10.dp)


        .verticalScroll(rememberScrollState())){

        reviews.reviews.forEachIndexed { _, item ->
            val ratingState = remember { mutableIntStateOf(item.score) }
            Box(Modifier.background(Color.Gray).height(130.dp)){


                Row{

                    Text(text = item.user, modifier = Modifier.padding(20.dp))
                    Text(text = item.review, modifier = Modifier
                        .padding(20.dp)
                        .width(400.dp)
                        .height(80.dp)
                        .verticalScroll(rememberScrollState()))

                }
                UserRatingBar(ratingState = ratingState, size = 30.dp, is_Locked = true, modifier = Modifier.padding(start =140.dp, top = 93.dp))





            }
            Spacer(modifier = Modifier.height(15.dp))

        }

    }

}


@Composable
fun DrawLine() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val startX = 50f
        val startY = 50f
        val endX = size.width - 50f
        val endY = size.height - 50f

        drawLine(
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            color = Color.Black, // Change color as needed
            strokeWidth = 5f // Change thickness as needed
        )
    }
}