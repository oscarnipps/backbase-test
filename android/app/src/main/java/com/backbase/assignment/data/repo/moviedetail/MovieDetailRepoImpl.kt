package com.backbase.assignment.data.repo.moviedetail

import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.mappers.MovieDetailMapper
import com.backbase.assignment.data.remote.httpservice.MovieDetailService
import com.backbase.assignment.ui.moviedetails.MovieDetail
import io.reactivex.Single
import javax.inject.Inject

class MovieDetailRepoImpl @Inject constructor(
    private val movieDetailService: MovieDetailService,
    private val mapper: MovieDetailMapper
) : MovieDetailRepo{

    override fun getMostPopularMovies(movieId : String): Single<MovieDetail> {
        val queryMap = mutableMapOf(
            Pair(Constants.QUERY_API_KEY, Constants.API_KEY),
            Pair(Constants.QUERY_LANGUAGE_KEY, Constants.LANGUAGE),
        )
        return movieDetailService.getMovieDetails(queryMap,movieId)
            .map { mapper.mapToMovieDetail(it) }
    }
}