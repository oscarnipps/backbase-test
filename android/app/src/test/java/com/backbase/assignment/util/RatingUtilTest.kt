package com.backbase.assignment.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RatingUtilTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `voting average returns valid movie rating number`() {
        val expectedRating = 67

        val votingAverage = 6.7

        val actualRating = RatingUtil.getMovieRatingValue(votingAverage)

        Truth.assertThat(actualRating).isEqualTo(expectedRating)

        Truth.assertThat(actualRating::class.simpleName).isEqualTo("Int")

    }

    @Test
    fun `voting average returns rating percent value`() {
        val expectedRatingPercent = "67%"

        val votingAverage = 6.7

        val actualRatingPercent = RatingUtil.getMovieRatingPercent(votingAverage)

        Truth.assertThat(actualRatingPercent).isEqualTo(expectedRatingPercent)

        Truth.assertThat(actualRatingPercent::class.simpleName).isEqualTo("String")
    }


}