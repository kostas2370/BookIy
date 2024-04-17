package com.example.bookindexer.data

import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getResults(): Flow<Result<BooksResponse>>
}