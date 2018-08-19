package me.gobetti.codechallenge.modules

import android.content.Context

interface ContextProvider {
    fun getContext(): Context?
}