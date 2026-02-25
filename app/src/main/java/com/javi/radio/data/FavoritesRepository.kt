package com.javi.radio.data

import android.content.Context
import com.javi.radio.domain.FavoriteStation
import org.json.JSONArray
import org.json.JSONObject

class FavoritesRepository(context: Context) {

    private val prefs = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
    private val KEY = "favorites_list"
    private val INITIALIZED_KEY = "app_initialized_v2"

    init {
        // Primera vez que se abre la app: cargar emisoras de Aragón por defecto
        if (!prefs.getBoolean(INITIALIZED_KEY, false)) {
            saveAll(defaultAragonStations())
            prefs.edit().putBoolean(INITIALIZED_KEY, true).apply()
        }
    }

    private fun defaultAragonStations(): List<FavoriteStation> = listOf(
        FavoriteStation("Aragón Deportes", 93.9f),
        FavoriteStation("Radio Ebro",      97.7f),
        FavoriteStation("Cadena 100",      97.2f),
        FavoriteStation("Europa FM",       88.2f),
        FavoriteStation("Hit FM",          91.5f),
        FavoriteStation("COPE",            95.0f)
    )

    fun getAll(): List<FavoriteStation> {
        val jsonString = prefs.getString(KEY, "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)
        val list = mutableListOf<FavoriteStation>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            list.add(
                FavoriteStation(
                    name = obj.getString("name"),
                    frequency = obj.getDouble("frequency").toFloat()
                )
            )
        }
        return list
    }

    fun saveAll(stations: List<FavoriteStation>) {
        val jsonArray = JSONArray()
        stations.forEach { station ->
            val obj = JSONObject()
            obj.put("name", station.name)
            obj.put("frequency", station.frequency)
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
