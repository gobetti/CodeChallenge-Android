package me.gobetti.codechallenge

import java.io.File

data class JSONString(private val path: String) {
    val value: String
        get() {
            val uri = javaClass.classLoader.getResourceAsStream("json/$path")
            return String(uri.readBytes())
        }
}