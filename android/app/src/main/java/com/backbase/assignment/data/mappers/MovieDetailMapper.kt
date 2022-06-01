package com.backbase.assignment.data.mappers

import com.backbase.assignment.data.remote.response.MovieDetailsResponse
import com.backbase.assignment.ui.moviedetails.MovieDetail
import javax.inject.Inject

class MovieDetailMapper @Inject constructor()  {

    fun mapToMovieDetail(movieDetailsResponse: MovieDetailsResponse): MovieDetail? {
        return null
    }
}