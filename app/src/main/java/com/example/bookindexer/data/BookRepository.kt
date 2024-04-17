package com.example.bookindexer.data

import kotlinx.coroutines.flow.Flow


interface BookRepository {
        suspend fun getBook(): Flow<Result<Book>>
    }
