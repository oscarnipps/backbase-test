package com.backbase.assignment.ui.mostpopular

data class MostPopularMovie(
    val movieId: Int,
    val movieTitle: String,
    val movieOverView: String,
    val movieImageUrl: String,
    val movieRating: Int
)