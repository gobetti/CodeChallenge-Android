package me.gobetti.codechallenge.di

import android.app.Application

class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.instance = AppServiceLocator
    }
}