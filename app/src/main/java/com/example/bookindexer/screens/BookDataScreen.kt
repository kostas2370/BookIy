package com.example.bookindexer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookindexer.data.Book

@Composable
fun bookData(book: Book){
    Column (
        Modifier.height(600.dp)
            .padding(top = 310.dp, start = 30.dp)
            .verticalScroll(rememberScrollState())) {

        Text("Short Description : \n   "+book.shortDescription, color = Color.Gray, fontSize = 17.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("ISBN :"+book.ISBN, color = Color.Gray, fontSize = 21.sp, textAlign = TextAlign.Center, modifier= Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Text("Author : "+book.author, color = Color.Gray, fontSize = 21.sp, textAlign = TextAlign.Center, modifier= Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Text("Publisher : "+book.publisher, color = Color.Gray, fontSize = 21.sp, textAlign = TextAlign.Center, modifier= Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Text("Release Date : "+book.publication_date, color = Color.Gray, fontSize = 21.sp, textAlign = TextAlign.Center, modifier= Modifier.fillMaxWidth())


    }

}