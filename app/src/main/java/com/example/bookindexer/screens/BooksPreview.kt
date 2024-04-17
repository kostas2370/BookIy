package com.example.bookindexer.screens

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookindexer.BookActivity
import com.example.bookindexer.data.Book

@Composable
fun BooksPreview(books: List<Book>) {
    val context = LocalContext.current

    Row {
        books.forEachIndexed { _, item ->
            Surface(onClick = {
                val intent = Intent(context, BookActivity::class.java)
                intent.putExtra("BOOK_ID", item.id.toString())
                intent.putExtra("IS_FAVOURITE", item.is_liked.toString())

                context.startActivity(intent)


            }) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = item.image,
                    contentDescription = item.title,
                    modifier = Modifier
                        .width(130.dp)
                        .height(173.dp)
                        .padding(10.dp)
                        .fillMaxSize()
                        .padding(start = 3.dp)
                )

            }

        }

    }

}
