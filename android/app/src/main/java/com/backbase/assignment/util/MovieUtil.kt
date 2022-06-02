package com.backbase.assignment.util

import com.backbase.assignment.common.Constants
import com.backbase.assignment.data.remote.response.MostPopularMoviesResponse
import com.backbase.assignment.ui.mostpopular.MostPopularMovie
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieUtil {

    companion object {

        @JvmStatic
        fun getMovieRatingValue(voteAverage: Double): Int {
            return ((voteAverage / 10) * 100).toInt()
        }

        @JvmStatic
        fun getMovieRatingPercent(voteAverage: Double): String {
            Timber.d("rating for movie : ${getMovieRatingValue(voteAverage)}")
            return String.format("%d%%", getMovieRatingValue(voteAverage))
        }

        fun getMovieImageUrl(posterPathUrl: String): String {
            return "${Constants.IMAGE_BASE_URL}${Constants.LARGE_IMAGE_FILE_SIZE}$posterPathUrl"
        }

        fun getFormattedMovieRuntimeString(totalMinutes: Int): String {
            var minutes = (totalMinutes % 60).toString()

            if (minutes.length == 1) {
                minutes = "0$minutes"
            }

            return (totalMinutes / 60).toString() + "h" + minutes
        }

        fun getFormattedMovieReleaseDateString(dateString: String): String {
            val localDate = LocalDate.parse(dateString, getFormatter("yyyy-MM-dd"))

            val format = getFormatter("MMMM, dd yyyy")

            return localDate.format(format)
        }

        private fun getFormatter(pattern: String): DateTimeFormatter {
            return DateTimeFormatter.ofPattern(pattern)
        }

    }
}