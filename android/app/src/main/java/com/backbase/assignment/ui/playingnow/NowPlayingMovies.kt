package com.backbase.assignment.ui.playingnow


data class NowPlayingMovies(
    val total: Int = 0,
    val page: Int = 0,
    val movies: List<NowPlayingMovie>
)

data class NowPlayingMovie(
    val movieId: Int,
    val movieTitle: String,
    val movieImageUrl: String,
    val movieRating: Double
)

