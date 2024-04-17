package com.example.bookindexer.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline

import androidx.compose.foundation.layout.width

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookindexer.BookActivity
import com.example.bookindexer.BookListActivity

import com.example.bookindexer.Model.HomeViewModel
import com.example.bookindexer.SearchActivity
import com.example.bookindexer.data.Book


@Composable
fun HomeScreen(viewModel: HomeViewModel){
    val books = viewModel.uiState.collectAsState()
    val bestbooks = books.value.best
    val trendingbooks = books.value.trending

    val context = LocalContext.current

    var search by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Browse Books", color = Color.Gray,  fontWeight = FontWeight.Bold ,fontSize = 30.sp,
            modifier = Modifier.paddingFromBaseline(top = 110.dp, bottom = 15.dp))
        OutlinedTextField(
            singleLine = true,
            value = search,
            onValueChange = {search = it},
            placeholder = {
                Text(text = "Search for books")
            },
            leadingIcon = {
                IconButton(onClick = {
                    val intent = Intent(context, SearchActivity::class.java)
                    intent.putExtra("SEARCH", search)

                    context.startActivity(intent)



                }){
                Icon(
                    Icons.Filled.Search,
                    tint = MaterialTheme.colorScheme.outline,
                    contentDescription = "Search"
                )
            }},
            modifier = Modifier
                .fillMaxWidth()
                .paddingFromBaseline(top = 40.dp, bottom = 15.dp)
        )


    }
    Column (modifier = Modifier
        .fillMaxSize()
        .paddingFromBaseline(top = 230.dp, bottom = 15.dp)

    ) {
        Box(Modifier.fillMaxWidth()){
            Text(text = "Trending now", color = Color.Gray,  fontWeight = FontWeight.Medium ,fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 14.dp))
            
            ClickableText(text = AnnotatedString("View all"), onClick = {
                val intent = Intent(context, BookListActivity::class.java)
                intent.putExtra("MODE", "trending")

                context.startActivity(intent)

            }, modifier = Modifier
                .align(Alignment.TopEnd)
                .paddingFromBaseline(27.dp)
                .padding(end = 20.dp))


        }

        BooksPreview(trendingbooks)
        Spacer(modifier = Modifier.height(10.dp))

        Box(Modifier.fillMaxWidth()){
            Text(text = "Top 100", color = Color.Gray,  fontWeight = FontWeight.Medium ,fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 14.dp))

            ClickableText(text = AnnotatedString("View all"), onClick = {
                val intent = Intent(context, BookListActivity::class.java)
                intent.putExtra("MODE", "best")

                context.startActivity(intent)

            }, modifier = Modifier
                .align(Alignment.TopEnd)
                .paddingFromBaseline(27.dp)
                .padding(end = 20.dp))


        }

        BooksPreview(bestbooks)

        }



}


@Composable
fun BooksPreview(books: List<Book>){
    val context = LocalContext.current

    Row {
        books.forEachIndexed { _, item ->
            Surface(onClick = {
                val intent = Intent(context, BookActivity::class.java)
                intent.putExtra("BOOK_ID", item.id.toString())
                intent.putExtra("IS_FAVOURITE", item.is_liked.toString())

                context.startActivity(intent)


            }){
                AsyncImage(contentScale = ContentScale.Crop,model = item.image, contentDescription =item.title , modifier = Modifier
                    .width(130.dp)
                    .height(173.dp)
                    .padding(10.dp)
                    .fillMaxSize()
                    .padding(start = 3.dp))

            }

        }

    }

}


