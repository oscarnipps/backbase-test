package com.backbase.assignment.util

import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import timber.log.Timber

class RatingUtil {

    companion object{

        @JvmStatic
        fun getMovieRatingValue(voteAverage: Double): Int {
            return ((voteAverage / 10) * 100).toInt()
        }

        @JvmStatic
        fun getMovieRatingPercent(voteAverage: Double): String {
            Timber.d("rating for movie : ${getMovieRatingValue(voteAverage)}")
            return String.format("%d%%" , getMovieRatingValue(voteAverage))
        }

    }
}