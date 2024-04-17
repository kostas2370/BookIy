package com.example.bookindexer.data

import com.example.bookindexer.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BookReviewRepositoryImpl(
    private val api: ApiService,
    private val token: String,
    private val id: String
) : BookReviewRepository {

    override suspend fun getReviews(): Flow<Result<BookReviewResponse>> {
        return flow {
            val bookReviews = try {
                api.fetchReviews(token = token, id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading books"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading books"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading books"))
                return@flow
            }

            emit(Result.Success(bookReviews))

        }
    }
}


