package com.javi.radio.data

import android.content.Context
import com.javi.radio.domain.FavoriteStation
import org.json.JSONArray
import org.json.JSONObject

class FavoritesRepository(context: Context) {

    private val prefs = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
    private val KEY = "favorites_list"

    fun getAll(): List<FavoriteStation> {
        val jsonString = prefs.getString(KEY, "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)

        val list = mutableListOf<FavoriteStation>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            list.add(
                FavoriteStation(
                    obj.getString("name"),
                    obj.getDouble("frequency").toFloat()
                )
            )
        }
        return list
    }

    fun saveAll(stations: List<FavoriteStation>) {
        val jsonArray = JSONArray()
        stations.forEach {
            val obj = JSONObject()
            obj.put("name", it.name)
            obj.put("frequency", it.frequency)
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY, jsonArray.toString()).apply()
    }

    fun add(station: FavoriteStation) {
        val current = getAll().toMutableList()
        current.add(station)
        saveAll(current)
    }

    fun remove(station: FavoriteStation) {
        val current = getAll().toMutableList()
        current.remove(station)
        saveAll(current)
    }
}
