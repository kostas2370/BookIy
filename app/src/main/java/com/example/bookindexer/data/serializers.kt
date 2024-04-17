package com.example.bookindexer.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(val access: String)

data class OwnReview(

    var score: Int,
    var review: String
)

data class Book(
    @SerializedName("id")
    var id: Int,
    var score: String,
    var title: String,
    var author: String,
    var image: String,
    var publication_date: String,
    var ISBN: String,
    var publisher: String,
    var shortDescription: String,
    var is_liked: Boolean,
    var ownreview: OwnReview

)

data class BooksResponse(

    @SerializedName("results")
    var Books: List<Book>
)

data class RegisterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("is_active")
    val is_active: Boolean

)

data class HomePageBooksResponse(
    var trending: List<Book>,
    var best: List<Book>
)

data class FavouriteBooksResponse(
    var count: Int,
    var books: List<Book>
)

data class BookReview(
    var user: String,
    var score: Int,
    var review: String

)

data class BookReviewResponse(
    var reviews: List<BookReview>
)

data class UpdateResponse(
    var message: String
)
