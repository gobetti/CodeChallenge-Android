package me.gobetti.codechallenge

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import android.os.SystemClock.sleep
import me.gobetti.codechallenge.di.serviceLocator
import okhttp3.mockwebserver.MockResponse

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun opens() {
        val mockResponse = MockResponse().setBody(JSONString("upcoming_200.json").value)
        (serviceLocator() as TestServiceLocator).enqueue(mockResponse)

        activityTestRule.launchActivity(null)

        sleep(2000)
    }
}
