package com.example.bookindexer.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookindexer.BookActivity
import com.example.bookindexer.R
import com.example.bookindexer.data.Book

@Composable
fun SearchBook(book: Book, context: Context = LocalContext.current) {
    val errorimage: Painter = painterResource(id = R.drawable.nocover)
    Surface(onClick = {

        val intent = Intent(context, BookActivity::class.java)
        intent.putExtra("BOOK_ID", book.id.toString())
        intent.putExtra("IS_FAVOURITE", book.is_liked.toString())

        context.startActivity(intent)

    }, modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()
        .background(Color.Gray)) {


        AsyncImage(
            model = book.image,
            contentDescription = book.title,
            error = errorimage,
            modifier = Modifier
                .width(130.dp)
                .height(173.dp)
                .padding(10.dp)
                .fillMaxSize()
                .padding(end = 250.dp)
        )
        Column(
            Modifier
                .padding(top = 10.dp, start = 160.dp)
        ) {
            Text(book.title, fontWeight = FontWeight.Bold)
            Text(book.ISBN, fontStyle = FontStyle.Italic)
            Text(book.author, fontFamily = FontFamily.Monospace)
            Text(book.publication_date)

        }
    }


}