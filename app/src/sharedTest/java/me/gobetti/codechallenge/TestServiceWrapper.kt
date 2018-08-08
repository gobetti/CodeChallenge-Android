package me.gobetti.codechallenge

import me.gobetti.codechallenge.service.TMDBService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class TestServiceWrapper {
    private lateinit var mockWebServer: MockWebServer
    lateinit var service: TMDBService

    init {
        // MockWebServer.start() is called by its constructor
        runInBackgroundSynchronously { completed ->
            mockWebServer = MockWebServer()
            service = TMDBService.create(mockWebServer.url("").toString())
            completed()
        }
    }

    fun start() {
        // MockWebServer.start() needs to run in a background thread, but we need to block until it completes
        runInBackgroundSynchronously { completed ->
            try {
                mockWebServer.start()
            } catch (e: IllegalStateException) {
                // already started (1st call happens from constructor)
            } finally {
                completed()
            }
        }
    }

    fun shutdown() {
        mockWebServer.shutdown()
    }

    fun enqueue(mockResponse: MockResponse) {
        mockWebServer.enqueue(mockResponse)
    }

    private fun runInBackgroundSynchronously(closure: (completed: () -> Unit) -> Unit) {
        val lock = Object()
        Thread(Runnable {
            closure {
                synchronized(lock) { lock.notify() }
            }
        }).start()
        synchronized(lock) { lock.wait() }
    }
}