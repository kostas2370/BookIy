package com.example.bookindexer.data

import kotlinx.coroutines.flow.Flow

interface BookReviewRepository {
    suspend fun getReviews(): Flow<Result<BookReviewResponse>>


}