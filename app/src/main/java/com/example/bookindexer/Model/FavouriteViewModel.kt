package com.example.bookindexer.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookindexer.data.FavouriteBooksResponse
import com.example.bookindexer.data.FavouriteRepository

import com.example.bookindexer.data.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class FavouriteViewModel(
        private val favouriteRepository: FavouriteRepository
    )
        : ViewModel() {
        private val _books = MutableStateFlow(FavouriteBooksResponse(0, emptyList()))
        val books = _books.asStateFlow()

        private val _showErrorToastChannel = Channel<Boolean>()
        val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

        init{
            loadFavourite()
        }


    fun loadFavourite(){
        viewModelScope.launch{
            favouriteRepository.getFavouriteBooks().collectLatest {result -> when(result){


                is Result.Success ->
                    result.data?.let { books ->
                        _books.update { books }
                    }
                is Result.Error -> {
                    _showErrorToastChannel.send(true)
                }
            } }


        }

    }
    }
