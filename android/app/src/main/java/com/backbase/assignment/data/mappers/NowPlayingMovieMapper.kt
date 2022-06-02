package com.backbase.assignment.data.mappers

import com.backbase.assignment.data.remote.response.NowPlayingMovieResponse
import com.backbase.assignment.ui.playingnow.NowPlayingMovie
import com.backbase.assignment.ui.playingnow.NowPlayingMovies
import com.backbase.assignment.util.MovieUtil
import timber.log.Timber
import javax.inject.Inject

class NowPlayingMovieMapper @Inject constructor() {

    fun mapToNowPlayingMovie(response: NowPlayingMovieResponse): NowPlayingMovies {

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