package me.gobetti.codechallenge

import android.app.Application
import me.gobetti.codechallenge.di.ServiceLocator

class TestApplication: Application() {
    private val serviceLocator = TestServiceLocator()

    override fun onCreate() {
        super.onCreate()

        serviceLocator.start()
        ServiceLocator.instance = serviceLocator
    }

    override fun onTerminate() {
        super.onTerminate()
        serviceLocator.shutdown()
    }
}