package com.backbase.assignment.data.remote.httpservice

import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.remote.response.MostPopularMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MostPopularMovieService {

    @GET(Constants.ENDPOINT_MOST_POPULAR_MOVIES)
    fun getMostPopularMovies(@QueryMap queryMa: MutableMap<String, String>): Single<MostPopularMoviesResponse>

}