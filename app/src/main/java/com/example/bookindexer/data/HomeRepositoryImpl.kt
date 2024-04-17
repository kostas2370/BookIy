package com.example.bookindexer.data

import com.example.bookindexer.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class HomeRepositoryImpl(private val api: ApiService, private val token: String) :
    HomeRepository {
    override suspend fun getHomeBooks(): Flow<Result<HomePageBooksResponse>> {
        return flow {
            val homeBooks = try {
                api.fetchHomePageBooks(token = token)
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

            emit(Result.Success(homeBooks))

        }
    }
}