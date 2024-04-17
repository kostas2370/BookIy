package com.example.bookindexer.api
import com.example.bookindexer.api.Constants
import com.example.bookindexer.data.Book
import com.example.bookindexer.data.BookReviewResponse
import com.example.bookindexer.data.BooksResponse
import com.example.bookindexer.data.FavouriteBooksResponse
import com.example.bookindexer.data.HomePageBooksResponse
import com.example.bookindexer.data.LoginResponse
import com.example.bookindexer.data.RegisterResponse
import com.example.bookindexer.data.UpdateResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @GET(Constants.BOOKS_URL)
    suspend fun searchBooks(@Header("Authorization") token: String, @Query("search") search : String?, @Query("mode") mode : String?): BooksResponse

    @POST(Constants.REGISTER_URL)
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String
    ): RegisterResponse

    @GET(Constants.HOMEPAGE_BOOKS_URL)
    suspend fun fetchHomePageBooks(@Header("Authorization") token: String): HomePageBooksResponse

    @GET(Constants.BOOKS_FAVOURITE_URL)
    suspend fun fetchFavouriteBooks(@Header("Authorization") token: String): FavouriteBooksResponse

    @GET(Constants.BOOKS_URL + "{book}/")
    suspend fun fetchBook(@Header("Authorization") token: String, @Path("book")book : String): Book

    @GET(Constants.BOOKS_URL + "{book}/review/")
    suspend fun fetchReviews(@Header("Authorization") token: String, @Path("book")book : String): BookReviewResponse

    @PATCH(Constants.BOOKS_URL + "{book}/like/")
    suspend fun likeBook(@Header("Authorization") token: String, @Path("book")book : String?): Book

    @POST(Constants.BOOKS_URL + "{book}/review/")
    @FormUrlEncoded
    suspend fun postReview(
        @Header("Authorization") token: String,
        @Path("book")book : String?,
        @Field("review") review: String,
        @Field("score") score: String,

        ): BookReviewResponse

    @PUT(Constants.UPDATE_URL)
    @FormUrlEncoded
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Field("username") username: String?,
        @Field("email") email: String?,
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String,


        ): UpdateResponse


}