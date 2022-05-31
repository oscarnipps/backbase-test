package com.backbase.assignment.ui.mostpopular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.backbase.assignment.data.repo.mostpopular.MostPopularRepo
import com.google.common.truth.Truth
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MostPopularViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mostPopularRepo: MostPopularRepo

    private lateinit var mostPopularViewModel: MostPopularViewModel

    @Before
    fun setUp() {
        mostPopularViewModel = MostPopularViewModel(mostPopularRepo)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `voting average returns valid movie rating number`() {
        val expectedRating = 67

        val votingAverage = 6.7

        val actualRating = mostPopularViewModel.getMovieRating(votingAverage)

        Truth.assertThat(actualRating).isEqualTo(expectedRating)

        Truth.assertThat(actualRating::class.simpleName).isEqualTo("Int")

    }
}