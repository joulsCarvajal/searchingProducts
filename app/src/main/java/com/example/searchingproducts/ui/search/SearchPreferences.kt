package com.example.searchingproducts.ui.search

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveRecentSearch(query: String) {
        val searches = getRecentSearches().toMutableList()

        searches.remove(query)
        searches.add(0, query)

        while (searches.size > 3) {
            searches.removeAt(searches.size - 1)
        }

        prefs.edit().putString("recent_searches", gson.toJson(searches)).apply()
    }

    fun getRecentSearches(): List<String> {
        val searchesJson = prefs.getString("recent_searches", "[]")
        return try {
            gson.fromJson(searchesJson, Array<String>::class.java).toList()
        } catch (e: Exception) {
            emptyList()
        }

    }
}