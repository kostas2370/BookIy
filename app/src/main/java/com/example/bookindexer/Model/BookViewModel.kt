package com.example.bookindexer.Model

import com.example.bookindexer.data.Book
import com.example.bookindexer.data.BookRepository


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookindexer.data.FavouriteBooksResponse
import com.example.bookindexer.data.FavouriteRepository
import com.example.bookindexer.data.OwnReview

import com.example.bookindexer.data.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class BookViewModel(
    private val bookRepository: BookRepository
)
    : ViewModel() {
    private val _book = MutableStateFlow(
        Book(0,"","","","","","", publisher = "",
            shortDescription = "", is_liked = false, ownreview = OwnReview(0,"")))

    val book = _book.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init{
        loadBook()
    }

    fun loadBook(){
        viewModelScope.launch{
            bookRepository.getBook().collectLatest {result -> when(result){


                is Result.Success ->
                    result.data?.let { books ->
                        _book.update { books }
                    }
                is Result.Error -> {
                    _showErrorToastChannel.send(true)
                }
            } }


        }

    }

}
