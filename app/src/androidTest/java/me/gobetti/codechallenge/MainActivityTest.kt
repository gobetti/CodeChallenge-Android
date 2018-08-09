package me.gobetti.codechallenge

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import me.gobetti.codechallenge.di.serviceLocator
import okhttp3.mockwebserver.MockResponse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    val serviceLocator = serviceLocator() as TestServiceLocator

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun loadsListOfMovies() {
        val mockResponse = MockResponse().setBody(JSONString("upcoming_200.json").value)
        serviceLocator.enqueue(mockResponse)

        activityTestRule.launchActivity(null)

        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText("Mission: Impossible - Fallout"))))
                .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText("The Endless"))))
    }

    @Test
    fun opensDetailsOnMovieClick() {
        val mockResponse = MockResponse().setBody(JSONString("upcoming_200.json").value)
        serviceLocator.enqueue(mockResponse)

        activityTestRule.launchActivity(null)

        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.moviePosterImageView))
                .check(matches(isDisplayed()))
    }
}
