package com.backbase.assignment.data.repo.mostpopular

import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.mappers.MostPopularMovieMapper
import com.backbase.assignment.data.remote.httpservice.MostPopularMovieService
import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import javax.inject.Inject

class MostPopularRepoImpl @Inject constructor(
    private val popularMovieService: MostPopularMovieService,
    private val mapper: MostPopularMovieMapper,
) : MostPopularRepo {

    override fun getMostPopularMovies(): List<MostPopularMovie> {

        return emptyList()
    }
}