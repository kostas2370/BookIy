package com.example.bookindexer.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookindexer.data.Book


@Composable
fun FavouritesScreen(favourite: List<Book>) {


    Text(
        text = favourite.size.toString() + " Entries",
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxWidth()
            .background(color = Color(0xFF3A30AB))
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .height(710.dp)
            .padding(top = 120.dp)
            .verticalScroll(rememberScrollState())
    ) {
        var index = 0
        while (index < favourite.size) {

            Row(
                horizontalArrangement = Arrangement.Absolute.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                for (i in 0 until 2) {
                    if (index < favourite.size) {
                        FavouriteBook(favourite[index])
                        index++
                    }
                }
            }

        }

    }

}




