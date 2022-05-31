package com.backbase.assignment.data.mappers

import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.remote.response.MostPopularMoviesResponse
import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import timber.log.Timber
import javax.inject.Inject

class MostPopularMovieMapper @Inject constructor() {

    fun mapToMostPopularMovies(movieResponse: MostPopularMoviesResponse): List<MostPopularMovie> {
        val mostPopularMovies = ArrayList<MostPopularMovie>()

        for (movieItem in movieResponse.results){

            val mostPopularMovie = MostPopularMovie(
                movieItem.id,
                movieItem.title,
                movieItem.overview,
                getImageUrl(movieItem),
                movieItem.voteAverage
            )

            mostPopularMovies.add(mostPopularMovie)
        }

        Timber.d("list size : ${mostPopularMovies.size}")

        return mostPopularMovies
    }

    private fun getImageUrl(movieItem: MostPopularMoviesResponse.MovieData): String {
        return "${Constants.IMAGE_BASE_URL}${Constants.LARGE_IMAGE_FILE_SIZE}/${movieItem.posterPath} "
    }
}