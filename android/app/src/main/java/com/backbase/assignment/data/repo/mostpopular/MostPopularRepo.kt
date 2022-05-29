package com.backbase.assignment.data.repo.mostpopular

import com.backbase.assignment.data.remote.response.MostPopularMoviesResponse
import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import retrofit2.http.QueryMap

interface MostPopularRepo {

    fun getMostPopularMovies(): List<MostPopularMovie>

}