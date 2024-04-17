package com.example.bookindexer.screens
import android.content.Intent
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bookindexer.BookActivity
import com.example.bookindexer.R
import com.example.bookindexer.data.Book



@Composable
fun FavouritesScreen(navController: NavController, favourite: List<Book>){


    Text(text = favourite.size.toString() +" Entries",
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier.padding(top =60.dp).fillMaxWidth().background(color = Color(0xFF3A30AB)))

    Column (horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
        .fillMaxWidth().height(710.dp).padding(top=120.dp)
        .verticalScroll(rememberScrollState())) {
        var index =0
        while (index < favourite.size){

            Row(horizontalArrangement = Arrangement.Absolute.Center,modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
                for (i in 0 until 2) {
                    if (index < favourite.size) {
                        favbook(favourite[index])
                        index++
                    }
                }
            }

        }

    }

}

@Composable
fun favbook(book : Book) {
    val errorimage: Painter = painterResource(id = R.drawable.nocover)
    val context = LocalContext.current

    Surface(onClick = {
        val intent = Intent(context, BookActivity::class.java)
        intent.putExtra("BOOK_ID", book.id.toString())
        intent.putExtra("IS_FAVOURITE", book.is_liked.toString())
        context.startActivity(intent) }){
        AsyncImage(error = errorimage, contentScale = ContentScale.Crop,model = book.image, contentDescription =book.title , modifier = Modifier
            .width(150.dp)
            .height(183.dp)
            .fillMaxSize()
            .padding(start = 10.dp ,top = 20.dp, end = 10.dp)

        )
        var leng = if (book.title.length > 10){
            9
        }else{
            book.title.length
        }
        Text(book.title.substring(0,leng)+"...", modifier = Modifier.padding(top = 190.dp, start = 40.dp))


    }
}


