package com.example.bookindexer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookindexer.Model.BookSearchViewModel
import com.example.bookindexer.api.RetrofitInstance
import com.example.bookindexer.data.SearchRepositoryImpl
import com.example.bookindexer.data.SessionManager
import com.example.bookindexer.ui.theme.BookIndexerTheme

class BookListActivity : ComponentActivity() {
    private lateinit var sessionManager: SessionManager

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken().toString()
        val MODE = intent.getStringExtra("MODE")
        val search_repository = SearchRepositoryImpl(api = RetrofitInstance.api,token,"",MODE)

        val viewModel by viewModels<BookSearchViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BookSearchViewModel(search_repository)
                            as T
                }
            }
        })

        super.onCreate(savedInstanceState)
        setContent {
            var books = viewModel.results.collectAsState().value
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

                        }, content = {
                            Column(
                                Modifier
                                    .verticalScroll(rememberScrollState())
                                    .padding(top = 70.dp)){
                                Text(
                                    text = "${MODE.toString().uppercase()} Books",
                                    fontSize = 30.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            books.Books.forEachIndexed { _, item ->
                                Column(Modifier.background(Color.Gray)) {

                                    searchbook(item)

                                }
                            }}})
                }
                }
            }
        }
    }


@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookIndexerTheme {
        Greeting2("Android")
    }
}