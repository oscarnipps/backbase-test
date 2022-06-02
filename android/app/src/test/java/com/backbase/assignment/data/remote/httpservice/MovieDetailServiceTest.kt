package com.backbase.assignment.data.remote.httpservice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.backbase.assignment.LiveDataTestUtil
import com.backbase.assignment.MockResponseFileReader
import com.backbase.assignment.RxJavaSchedulerRule
import com.backbase.assignment.TestServiceGenerator
import com.backbase.assignment.common.Resource
import com.backbase.assignment.data.mappers.MostPopularMovieMapper
import com.backbase.assignment.data.mappers.MovieDetailMapper
import com.backbase.assignment.data.repo.moviedetail.MovieDetailRepo
import com.backbase.assignment.data.repo.moviedetail.MovieDetailRepoImpl
import com.backbase.assignment.ui.mostpopular.MostPopularViewModel
import com.backbase.assignment.ui.moviedetails.MovieDetailViewModel
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
class MovieDetailServiceTest {
    private val mockWebServer = MockWebServer()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val rxJavaSchedulerRule = RxJavaSchedulerRule()

    private lateinit var repo: MovieDetailRepo

    private var responsePath = "responses/moviedetail"

    private lateinit var movieDetailService: MovieDetailService

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        mockWebServer.start(8000)

        val baseUrl = mockWebServer.url("/").toString()

        movieDetailService = TestServiceGenerator.getService(
            MovieDetailService::class.java,
            baseUrl, mockWebServer.port
        )

        repo = MovieDetailRepoImpl(movieDetailService, MovieDetailMapper())

        movieDetailViewModel = MovieDetailViewModel(repo)
    }

    @Test
    fun `non null movie details retrieved from api successfully`() {
        val responseBody =
            MockResponseFileReader.content("$responsePath/movie-details-success-response.json")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )

        movieDetailViewModel.getMovieDetails("338953")

        val result = LiveDataTestUtil.getOrAwaitValue(movieDetailViewModel.getMovieDetail())

        Truth.assertThat(result?.status).isEqualTo(Resource.Status.SUCCESS)

        Truth.assertThat(result?.data).isNotNull()
    }

    @Test
    fun `invalid movie id key fails with non empty error message`() {
        val responseBody =
            MockResponseFileReader.content("$responsePath/invalid-movie-id-response.json")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(responseBody)
        )

        movieDetailViewModel.getMovieDetails("")

        val result = LiveDataTestUtil.getOrAwaitValue(movieDetailViewModel.getMovieDetail())

        Truth.assertThat(result?.status).isEqualTo(Resource.Status.ERROR)

        Truth.assertThat(result?.message).isNotEmpty()
    }


    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }

}