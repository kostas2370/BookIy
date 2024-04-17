package com.example.bookindexer.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.bookindexer.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class SearchRepositoryImpl(private val api : ApiService, private val token : String, private val search_term : String?, private val mode  : String? = "list"):SearchRepository {



    var searchQuery by mutableStateOf(search_term)
    public set

    override suspend fun getResults(): Flow<Result<BooksResponse>> {

        return flow {
            val searchBooks = try {
                api.searchBooks(token = token, searchQuery, mode)
            }catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading books"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading books"))
                return@flow
            }  catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading books"))
                return@flow
            }

            emit(Result.Success(searchBooks))

        }
    }


}