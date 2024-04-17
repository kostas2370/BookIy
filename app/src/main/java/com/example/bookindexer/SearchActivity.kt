package com.example.bookindexer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.bookindexer.Model.BookSearchViewModel
import com.example.bookindexer.api.RetrofitInstance
import com.example.bookindexer.data.Book
import com.example.bookindexer.data.SearchRepositoryImpl
import com.example.bookindexer.data.SessionManager
import com.example.bookindexer.ui.theme.BookIndexerTheme

class SearchActivity : ComponentActivity() {
    private lateinit var sessionManager: SessionManager

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken().toString()

        super.onCreate(savedInstanceState)
        val SEARCH = intent.getStringExtra("SEARCH")

        val search_repository =SearchRepositoryImpl(api = RetrofitInstance.api,token,SEARCH)



        val viewModel by viewModels<BookSearchViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BookSearchViewModel(search_repository)
                            as T
                }
            }
        })


        setContent {
            val books = viewModel.results.collectAsState().value

            BookIndexerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(

                        topBar = {
                            TopAppBar(title = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Bookly",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    textAlign = TextAlign.Center
                                )
                            }, colors =
                            TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White,
                            ), navigationIcon = {
                                IconButton(onClick = { this.finish() }) {
                                    Icon(
                                        Icons.Filled.ArrowBack ,
                                        contentDescription = null,
                                        tint = Color.White
                                    )

                                }

                            })

                        },

                        content = {
                            var searchl by remember { mutableStateOf(SEARCH.toString()) }
                            val context = LocalContext.current

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Text(text = "Search Result for :", color = Color.Gray,  fontWeight = FontWeight.Bold ,fontSize = 30.sp,
                                    modifier = Modifier.paddingFromBaseline(top = 110.dp, bottom = 15.dp))
                                OutlinedTextField(
                                    singleLine = true,
                                    value = searchl,
                                    onValueChange = {searchl = it},
                                    placeholder = {
                                        Text(text = "Search for books")
                                    },
                                    leadingIcon = {
                                        IconButton(onClick = {
                                            search_repository.searchQuery = searchl
                                            viewModel.loadSearchBook()



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
                                Column(Modifier.verticalScroll(rememberScrollState())){
                                books.Books.forEachIndexed { _, item ->
                                    Column(Modifier.background(Color.Gray)) {

                                            searchbook(item)

                                    }
                                }





                            }
                            }

                        },
                        bottomBar = {  }

                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String?, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun searchbook(book: Book, context: Context = LocalContext.current){
    val errorimage: Painter = painterResource(id = R.drawable.nocover)
    Surface(onClick = {

        val intent = Intent(context, BookActivity::class.java)
        intent.putExtra("BOOK_ID", book.id.toString())
        intent.putExtra("IS_FAVOURITE", book.is_liked.toString())

        context.startActivity(intent)

    } , modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()
        .background(Color.Gray)){


        AsyncImage(model = book.image, contentDescription = book.title, error = errorimage, modifier = Modifier
            .width(130.dp)
            .height(173.dp)
            .padding(10.dp)
            .fillMaxSize()
            .padding(end = 250.dp))
        Column (
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