package com.example.bookindexer.data

import kotlinx.coroutines.flow.Flow


interface FavouriteRepository {
        suspend fun getFavouriteBooks(): Flow<Result<FavouriteBooksResponse>>
    }
