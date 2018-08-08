package me.gobetti.codechallenge.di

import me.gobetti.codechallenge.service.TMDBService

fun serviceLocator() = ServiceLocator.instance // global

interface ServiceLocator {
    companion object {
        lateinit var instance: ServiceLocator
    }

    val tmdbService: TMDBService
}

internal object AppServiceLocator: ServiceLocator {
    override val tmdbService = TMDBService.create()
}