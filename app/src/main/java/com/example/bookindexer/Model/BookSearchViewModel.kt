package com.example.bookindexer.Model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookindexer.data.Book
import com.example.bookindexer.data.BookReviewRepository
import com.example.bookindexer.data.BooksResponse
import com.example.bookindexer.data.OwnReview
import com.example.bookindexer.data.Result
import com.example.bookindexer.data.SearchRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookSearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _results = MutableStateFlow(
        BooksResponse(
            emptyList()
        )
    )

    val results = _results.asStateFlow()

    init {
        loadSearchBook()
    }


    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun loadSearchBook(){
        viewModelScope.launch{
            searchRepository.getResults().collectLatest {result -> when(result) {


                is Result.Success ->

                    result.data?.let { books ->
                        _results.update { books }
                    }

                is Result.Error -> {
                    _showErrorToastChannel.send(true)
            }

            }
            }
    }
}}