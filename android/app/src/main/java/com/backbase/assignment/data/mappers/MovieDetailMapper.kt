package com.backbase.assignment.data.mappers

import com.backbase.assignment.data.remote.response.MovieDetailsResponse
import com.backbase.assignment.ui.moviedetails.MovieDetail
import com.backbase.assignment.util.MovieUtil
import timber.log.Timber
import javax.inject.Inject

class MovieDetailMapper @Inject constructor()  {

    fun mapToMovieDetail(response: MovieDetailsResponse): MovieDetail {

        Timber.d("image url : ${MovieUtil.getMovieImageUrl(response.posterPath)}")
        return MovieDetail(
            response.originalTitle,
            response.overview,
            MovieUtil.getMovieImageUrl(response.posterPath),
            MovieUtil.getFormattedMovieRuntimeString(response.runtime),
            MovieUtil.getFormattedMovieReleaseDateString(response.releaseDate),
            response.spokenLanguages[0].name,
            response.genres.map { it.name }
        )
    }
}