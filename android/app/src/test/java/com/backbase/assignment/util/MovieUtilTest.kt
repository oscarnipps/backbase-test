package com.backbase.assignment.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieUtilTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `voting average returns valid movie rating number`() {
        val expectedRating = 67

        val votingAverage = 6.7

        val actualRating = MovieUtil.getMovieRatingValue(votingAverage)

        Truth.assertThat(actualRating).isEqualTo(expectedRating)

        Truth.assertThat(actualRating::class.simpleName).isEqualTo("Int")

    }

    @Test
    fun `voting average returns rating percent value`() {
        val expectedRatingPercent = "67%"

        val votingAverage = 6.7

        val actualRatingPercent = MovieUtil.getMovieRatingPercent(votingAverage)

        Truth.assertThat(actualRatingPercent).isEqualTo(expectedRatingPercent)

        Truth.assertThat(actualRatingPercent::class.simpleName).isEqualTo("String")
    }

    @Test
    fun `movie minutes returns runtime string format`() {
        val expectedRuntimeFormat = "1h30"

        val movieMinutes = 90

        val actualRuntimeFormat = MovieUtil.getFormattedMovieRuntimeString(movieMinutes)

        Truth.assertThat(actualRuntimeFormat).isEqualTo(expectedRuntimeFormat)
    }

    @Test
    fun `movie release date converted to custom formatted date value `() {
        val expectedDateValue = "February, 06 2022"

        val movieReleaseDate = "2022-02-06"

        val actualDateValue = MovieUtil.getFormattedMovieReleaseDateString(movieReleaseDate)

        Truth.assertThat(actualDateValue).isEqualTo(expectedDateValue)
    }


}