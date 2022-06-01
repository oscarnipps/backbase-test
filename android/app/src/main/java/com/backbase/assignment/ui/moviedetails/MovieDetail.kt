package com.backbase.assignment.ui.moviedetails

import com.backbase.assignment.data.remote.response.MovieDetailsResponse

data class MovieDetail (
    val movieTitle: String,
    val movieOverView: String,
    val movieImageUrl: String,
    val movieDuration: String,
    val movieReleaseDate: String,
    val language: String,
    val genreList: List<String>
)