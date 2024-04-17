package com.example.bookindexer.data

import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    suspend fun getHomeBooks(): Flow<Result<HomePageBooksResponse>>
}