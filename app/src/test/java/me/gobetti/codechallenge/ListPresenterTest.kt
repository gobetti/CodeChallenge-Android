package me.gobetti.codechallenge

import android.content.Context

import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.modules.list.ListContract
import me.gobetti.codechallenge.modules.list.ListPresenter
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ListPresenterTest {
    private val serviceWrapper = TestServiceWrapper()

    @Before
    fun setUp() {
        serviceWrapper.start()
    }

    @After
    fun tearDown() {
        serviceWrapper.shutdown()
    }

    @Test
    fun tellsViewToDisplayMovies() {
        val expectation = Object()

        var wasCalled = false
        val sut = ListPresenter(ListViewMock {
            wasCalled = true
            synchronized(expectation) { expectation.notify() }
        }, serviceWrapper.service)

        val mockResponse = MockResponse().setBody(JSONString("upcoming_200.json").value)
        serviceWrapper.enqueue(mockResponse)

        //
        sut.fetchMovies()
        //

        synchronized(expectation) { expectation.wait(1000) }
        assertTrue(wasCalled)
    }
}

private data class ListViewMock(
        private val displayMoviesCalled: () -> Unit
): ListContract.View {
    override fun displayMovies(movies: List<Movie>) {
        displayMoviesCalled()
    }

    override fun getContext(): Context? = null
}