package com.example.bookindexer.data

import com.example.bookindexer.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FavouriteRepositoryImpl(private val api: ApiService, private val token: String) :
    FavouriteRepository {

    override suspend fun getFavouriteBooks(): Flow<Result<FavouriteBooksResponse>> {
        return flow {
            val favBooks = try {
                api.fetchFavouriteBooks(token = token)
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

            emit(Result.Success(favBooks))

        }
    }

}