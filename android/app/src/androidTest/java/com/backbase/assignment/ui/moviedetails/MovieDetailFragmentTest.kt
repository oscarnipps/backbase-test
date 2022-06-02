package com.backbase.assignment.ui.moviedetails

import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.backbase.assignment.R
import com.backbase.assignment.launchFragmentInHiltContainer
import com.backbase.assignment.ui.mostpopular.MostPopularFragment
import com.backbase.assignment.util.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class MovieDetailFragmentTest{

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }


    @Test
    fun movie_detail_view_is_visible() {
        val movieId = "752623"

        val args = bundleOf(Pair("movieId",movieId))

        launchFragmentInHiltContainer<MovieDetailFragment> (args)

        onView(withId(R.id.close))
            .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun movie_details_are_shown() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val movieId = "752623"

        val args = bundleOf(Pair("movieId",movieId))

        launchFragmentInHiltContainer<MovieDetailFragment>(args) {

            navController.setGraph(R.navigation.movie_nav_graph)

            navController.setCurrentDestination(R.id.movieDetailFragment)

            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.released_date))
            .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.duration))
            .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.movie_overview))
            .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.movie_language))
            .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun movie_genres_are_shown() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        val movieId = "752623"

        val args = bundleOf(Pair("movieId",movieId))

        launchFragmentInHiltContainer<MovieDetailFragment>(args) {
            navController.setGraph(R.navigation.movie_nav_graph)

            navController.setCurrentDestination(R.id.movieDetailFragment)

            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.genre_container))
            .check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.genre_container))
            .check(ViewAssertions.matches(hasMinimumChildCount(1)))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}