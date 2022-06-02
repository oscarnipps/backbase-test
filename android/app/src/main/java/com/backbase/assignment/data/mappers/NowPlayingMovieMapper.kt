package com.backbase.assignment.data.mappers

import com.backbase.assignment.data.remote.response.NowPlayingMovieResponse
import com.backbase.assignment.ui.playingnow.NowPlayingMovie
import com.backbase.assignment.ui.playingnow.NowPlayingMovies
import com.backbase.assignment.util.MovieUtil
import timber.log.Timber
import javax.inject.Inject

class NowPlayingMovieMapper @Inject constructor() {

    fun mapToNowPlayingMovie(response: NowPlayingMovieResponse): NowPlayingMovies {

        /* return MovieDetail(
             response.originalTitle,
             response.overview,
             MovieUtil.getMovieImageUrl(response.posterPath),
             MovieUtil.getFormattedMovieRuntimeString(response.runtime),
             MovieUtil.getFormattedMovieReleaseDateString(response.releaseDate),
             response.spokenLanguages[0].name,
             response.genres.map { it.name }
         )*/

        return with(response) {
            NowPlayingMovies(
                total = response.totalPages,
                page = response.page,
                movies = results.map {
                    NowPlayingMovie(
                        it.id,
                        it.title,
                        MovieUtil.getMovieImageUrl(it.posterPath),
                        it.voteAverage
                    )
                }
            )
        }
    }
}