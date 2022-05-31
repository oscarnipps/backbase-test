package com.backbase.assignment.data.remote.httpservice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.backbase.assignment.LiveDataTestUtil
import com.backbase.assignment.MockResponseFileReader
import com.backbase.assignment.RxJavaSchedulerRule
import com.backbase.assignment.TestServiceGenerator
import com.backbase.assignment.common.Resource
import com.backbase.assignment.data.mappers.MostPopularMovieMapper
import com.backbase.assignment.data.repo.mostpopular.MostPopularRepo
import com.backbase.assignment.data.repo.mostpopular.MostPopularRepoImpl
import com.backbase.assignment.ui.mostpopular.MostPopularViewModel
import com.google.common.truth.Truth
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MostPopularMovieServiceTest {

    private val mockWebServer = MockWebServer()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val rxJavaSchedulerRule = RxJavaSchedulerRule()

    private lateinit var repo: MostPopularRepo

    private var responsePath = "responses/mostpopularmovies"

    private lateinit var mostPopularMovieService: MostPopularMovieService

    private lateinit var mostPopularViewModel: MostPopularViewModel

    @Before
    fun setUp() {
        mockWebServer.start(8000)

        val baseUrl = mockWebServer.url("/").toString()

        mostPopularMovieService = TestServiceGenerator.getService(
            MostPopularMovieService::class.java,
            baseUrl, mockWebServer.port
        )

        repo = MostPopularRepoImpl(mostPopularMovieService, MostPopularMovieMapper())

        mostPopularViewModel = MostPopularViewModel(repo)
    }


    @Test
    fun `retrieves non-empty most popular movies list successfully`() {
        val responseBody =
            MockResponseFileReader.content("$responsePath/popular-movies-success-response.json")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )

        mostPopularViewModel.getMostPopularMovies()

        val result = LiveDataTestUtil.getOrAwaitValue(mostPopularViewModel.mostPopularMovies())

        Truth.assertThat(result?.status).isEqualTo(Resource.Status.SUCCESS)

        Truth.assertThat(result?.data).isNotEmpty()
    }

    @Test
    fun `invalid api key returns failure response with message`() {
        val responseBody =
            MockResponseFileReader.content("$responsePath/popular-movies-error-response.json")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody(responseBody)
        )

        mostPopularViewModel.getMostPopularMovies()

        val result = LiveDataTestUtil.getOrAwaitValue(mostPopularViewModel.mostPopularMovies())

        Truth.assertThat(result?.status).isEqualTo(Resource.Status.ERROR)

        Truth.assertThat(result?.message).isNotEmpty()
    }

    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }
}