package com.example.bookindexer.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookindexer.data.Book
import com.example.bookindexer.data.BookRepository
import com.example.bookindexer.data.BookReview
import com.example.bookindexer.data.BookReviewRepository
import com.example.bookindexer.data.BookReviewResponse
import com.example.bookindexer.data.OwnReview
import com.example.bookindexer.data.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookReviewViewModel(private val reviewRepository: BookReviewRepository) : ViewModel() {
    private val _reviews= MutableStateFlow(
        BookReviewResponse(emptyList())

        )

    val reviews = _reviews.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()
    init{
        loadReview()
    }

    fun loadReview(){
        viewModelScope.launch{
            reviewRepository.getReviews().collectLatest {result -> when(result){


                is Result.Success ->
                    result.data?.let { reviews ->
                        _reviews.update { reviews }
                    }
                is Result.Error -> {
                    _showErrorToastChannel.send(true)
                }
            } }


        }

    }
}