package com.example.bookindexer.data

import com.example.bookindexer.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BookRepositoryImpl(
    private val api: ApiService,
    private val token: String,
    private val id: String
) : BookRepository {
    override suspend fun getBook(): Flow<Result<Book>> {
        return flow {
            val book = try {
                api.fetchBook(token = token, id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading book"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading book"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading book"))
                return@flow
            }

            emit(Result.Success(book))

        }
    }


}