package me.gobetti.codechallenge

import junit.framework.Assert.assertEquals
import me.gobetti.codechallenge.service.TMDBService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

import java.io.File

class TMDBServiceTest {
    private val mockWebServer = MockWebServer()
    private val sut = TMDBService.create(mockWebServer.url("").toString())

    @Before
    fun setUp() {
        try {
            mockWebServer.start()
        } catch (e: IllegalStateException) {

        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun mappingIsCorrect() {
        val mockResponse = MockResponse().setBody(JSONString("upcoming_200.json").value)
        mockWebServer.enqueue(mockResponse)

        val response = sut.getUpcomingMovies(1).execute().body()
        assertEquals(response?.movies?.size, 20)

        val movie = response?.movies?.first()
        assertEquals(movie?.voteCount, 224)
        assertEquals(movie?.id, 353081)
        assertEquals(movie?.video, false)
        assertEquals(movie?.voteAverage, 7.6)
        assertEquals(movie?.title, "Mission: Impossible - Fallout")
        assertEquals(movie?.popularity, 445.872)
        assertEquals(movie?.posterPath, "/AkJQpZp9WoNdj7pLYSj1L0RcMMN.jpg")
        assertEquals(movie?.originalLanguage, "en")
        assertEquals(movie?.originalTitle, "Mission: Impossible - Fallout")
        assertEquals(movie?.genreIds, listOf(12, 28, 53))
        assertEquals(movie?.backdropPath, "/5qxePyMYDisLe8rJiBYX8HKEyv2.jpg")
        assertEquals(movie?.adult, false)
        assertEquals(movie?.overview, "When an IMF mission ends badly, the world is faced with dire consequences. As Ethan Hunt takes it upon himself to fulfil his original briefing, the CIA begin to question his loyalty and his motives. The IMF team find themselves in a race against time, hunted by assassins while trying to prevent a global catastrophe.")
        assertEquals(movie?.releaseDate, "2018-07-25")
    }
}
