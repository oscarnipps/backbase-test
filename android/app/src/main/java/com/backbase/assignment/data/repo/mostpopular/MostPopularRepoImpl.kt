package com.backbase.assignment.data.repo.mostpopular

import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.mappers.MostPopularMovieMapper
import com.backbase.assignment.data.remote.httpservice.MostPopularMovieService
import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import io.reactivex.Single
import javax.inject.Inject

class MostPopularRepoImpl @Inject constructor(
    private val popularMovieService: MostPopularMovieService,
    private val mapper: MostPopularMovieMapper,
) : MostPopularRepo {

    override fun getMostPopularMovies(): Single<List<MostPopularMovie>> {
        val queryMap = mutableMapOf(
            Pair(Constants.QUERY_API_KEY, Constants.API_KEY),
            Pair(Constants.QUERY_LANGUAGE_KEY, Constants.LANGUAGE),
        )

        return popularMovieService
            .getMostPopularMovies(queryMap)
            .map { mapper.mapToMostPopularMovies(it) }
    }

}