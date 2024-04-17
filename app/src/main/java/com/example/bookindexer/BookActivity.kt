package com.example.bookindexer

import android.annotation.SuppressLint
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookindexer.Model.BookReviewViewModel
import com.example.bookindexer.Model.BookViewModel
import com.example.bookindexer.api.RetrofitInstance
import com.example.bookindexer.data.Book
import com.example.bookindexer.data.BookRepositoryImpl
import com.example.bookindexer.data.BookReviewRepositoryImpl
import com.example.bookindexer.data.BookReviewResponse
import com.example.bookindexer.data.SessionManager
import com.example.bookindexer.screens.BookScreen
import com.example.bookindexer.screens.BookUserReview
import com.example.bookindexer.screens.BookData
import com.example.bookindexer.screens.BookReview
import com.example.bookindexer.screens.Bookbar
import com.example.bookindexer.ui.theme.BookIndexerTheme


class BookActivity : ComponentActivity() {
    private lateinit var sessionManager: SessionManager

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken().toString()

        val bookID = intent.getStringExtra("BOOK_ID")
        val isFavourite = intent.getStringExtra("IS_FAVOURITE")

        val bookViewModel by viewModels<BookViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BookViewModel(
                        BookRepositoryImpl(
                            RetrofitInstance.api,
                            token, bookID.toString()
                        )
                    )
                            as T
                }
            }
        })

        val reviewViewModel by viewModels<BookReviewViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BookReviewViewModel(
                        BookReviewRepositoryImpl(
                            RetrofitInstance.api,
                            token, bookID.toString()
                        )
                    )
                            as T
                }
            }
        })


        super.onCreate(savedInstanceState)



        setContent {
            val navController = rememberNavController()


            val book: Book = bookViewModel.book.collectAsState().value
            val reviews: BookReviewResponse = reviewViewModel.reviews.collectAsState().value


            BookIndexerTheme {

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
                                        Icons.Filled.ArrowBack,
                                        contentDescription = null,
                                        tint = Color.White
                                    )

                                }

                            })

                        },

                        content = {

                            BookScreen(book, isFavourite.toBoolean(), token, bookID)

                            NavHost(navController = navController, startDestination = "Info") {

                                composable("Info") { BookData(book) }
                                composable("My Review") {
                                    BookReview(
                                        id = bookID,
                                        token,
                                        navController,
                                        bookViewModel,
                                        reviewViewModel,
                                        book.ownreview
                                    )
                                }
                                composable("User Reviews") { BookUserReview(reviews = reviews) }
                            }


                        },
                        bottomBar = { Bookbar(navController) }

                    )
                }
            }
        }
    }
}




