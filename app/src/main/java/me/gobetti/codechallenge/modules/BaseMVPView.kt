package me.gobetti.codechallenge.modules

import android.arch.lifecycle.LifecycleOwner
import android.content.Context

interface BaseMVPView: LifecycleOwner {
    fun getContext(): Context?
}