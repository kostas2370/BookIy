package com.example.bookindexer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookindexer.R
import com.example.bookindexer.api.RetrofitInstance
import com.example.bookindexer.data.Book
import kotlinx.coroutines.launch

@Composable
fun BookScreen(book: Book, isFavourite: Boolean, token: String, id: String?) {
    val coroutineScope = rememberCoroutineScope()
    val errorimage: Painter = painterResource(id = R.drawable.nocover)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.Black)
    ) {

    }
    Text(
        book.score, color = Color.Gray, fontSize = 30.sp, modifier = Modifier
            .padding(top = 86.dp, start = 315.dp)
            .width(150.dp)
    )
    Icon(
        Icons.Outlined.Star,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier
            .padding(top = 90.dp, start = 265.dp)
            .size(40.dp)
    )

    AsyncImage(
        error = errorimage, model = book.image, contentDescription = book.title, modifier = Modifier
            .width(160.dp)
            .height(273.dp)
            .padding(top = 70.dp, start = 1.dp)
            .fillMaxSize()
    )


    Surface(
        shape = CircleShape,
        modifier = Modifier
            .padding(top = 45.dp, start = 155.dp)
            .height(90.dp)
            .width(45.dp),
        color = Color(0x77000000)
    ) {
        var isLiked by remember { mutableStateOf(isFavourite) }
        Icon(
            tint = Color.White,
            modifier = Modifier
                .clickable(onClick = {
                    coroutineScope.launch {
                        try {
                            RetrofitInstance.api
                                .likeBook(token, id)

                            isLiked = !isLiked

                        } catch (e: Exception) {
                            println("Error")
                        }
                    }
                })
                .graphicsLayer {
                    scaleX = 0.7f
                    scaleY = 0.7f
                },
            imageVector = if (isLiked) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )


    }
    Text(
        book.title, color = Color.Gray, fontSize = 25.sp, modifier = Modifier
            .padding(top = 150.dp, start = 175.dp)
            .height(350.dp)
            .width(200.dp)
    )


    Spacer(modifier = Modifier.height(100.dp))


}

