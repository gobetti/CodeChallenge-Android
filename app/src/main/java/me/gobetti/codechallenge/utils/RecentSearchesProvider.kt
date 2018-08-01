package me.gobetti.codechallenge.utils

import android.content.SearchRecentSuggestionsProvider

class RecentSearchesProvider: SearchRecentSuggestionsProvider() {
    companion object {
        const val AUTHORITY = "me.gobetti.CodeChallenge.RecentSearchesProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }

    init {
        super.setupSuggestions(AUTHORITY, MODE)
    }
}