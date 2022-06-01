package com.backbase.assignment.data.repo.moviedetail

import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import com.backbase.assignment.ui.moviedetails.MovieDetail
import io.reactivex.Single

interface MovieDetailRepo {

    fun getMostPopularMovies(movieId : String): Single<MovieDetail>
}