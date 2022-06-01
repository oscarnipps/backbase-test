package com.backbase.assignment.ui.mostpopular

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.backbase.assignment.R
import com.backbase.assignment.launchFragmentInHiltContainer
import com.backbase.assignment.util.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MostPopularFragmentTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun popular_movies_view_is_visible() {
        launchFragmentInHiltContainer<MostPopularFragment> { }

        onView(withId(R.id.header_title)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.header_title)).check(matches(isDisplayed()))
    }

    @Test
    fun navigate_to_movie_details() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        launchFragmentInHiltContainer<MostPopularFragment> {
            navController.setGraph(R.navigation.movie_nav_graph)

            navController.setCurrentDestination(R.id.mostPopularFragment)

            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.seeMore)).perform(ViewActions.click())

        assertTrue(navController.currentDestination!!.id == R.id.movieDetailFragment)
    }

    @Test
    fun navigate_to_movie_details_when_movie_item_is_clicked() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        launchFragmentInHiltContainer<MostPopularFragment> {
            navController.setGraph(R.navigation.movie_nav_graph)

            navController.setCurrentDestination(R.id.mostPopularFragment)

            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.popular_movies_view_pager)).perform(swipeLeft())

        onView(allOf(withId(R.id.movie_image), isDisplayed())).perform(click());

        assertTrue(navController.currentDestination!!.id == R.id.movieDetailFragment)
    }
}