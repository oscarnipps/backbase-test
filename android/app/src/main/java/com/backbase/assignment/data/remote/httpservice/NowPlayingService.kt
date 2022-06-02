package com.backbase.assignment.data.remote.httpservice

import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.remote.response.MovieDetailsResponse
import com.backbase.assignment.data.remote.response.NowPlayingMovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface NowPlayingService {

    @GET(Constants.ENDPOINT_NOW_PLAYING)
    fun getNowPlayingMovies(
        @QueryMap queryMap: MutableMap<String, String>
    ): Single<NowPlayingMovieResponse>
}