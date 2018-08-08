package me.gobetti.codechallenge

import me.gobetti.codechallenge.di.ServiceLocator
import me.gobetti.codechallenge.service.TMDBService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class TestServiceLocator: ServiceLocator {
    private val serviceWrapper = TestServiceWrapper()
    override val tmdbService: TMDBService = serviceWrapper.service

    fun start() {
        serviceWrapper.start()
    }

    fun shutdown() {
        serviceWrapper.shutdown()
    }

    fun enqueue(mockResponse: MockResponse) {
        serviceWrapper.enqueue(mockResponse)
    }
}