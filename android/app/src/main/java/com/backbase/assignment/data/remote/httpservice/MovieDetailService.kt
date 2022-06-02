package com.backbase.assignment.data.remote.httpservice

import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.remote.response.MostPopularMoviesResponse
import com.backbase.assignment.data.remote.response.MovieDetailsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MovieDetailService {

    @GET("${Constants.ENDPOINT_MOVIE_DETAILS}/{movieId}")
    fun getMovieDetails(
        @Path("movieId") movieId: String,
        @QueryMap queryMap: MutableMap<String, String>
    ): Single<MovieDetailsResponse>
}