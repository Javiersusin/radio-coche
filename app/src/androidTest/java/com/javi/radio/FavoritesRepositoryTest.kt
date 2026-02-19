package com.javi.radio

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.javi.radio.data.FavoritesRepository
import com.javi.radio.domain.FavoriteStation
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FavoritesRepositoryTest {

    private lateinit var repository: FavoritesRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        repository = FavoritesRepository(context)
        repository.saveAll(emptyList())
    }

    @Test
    fun addStation_shouldBeSaved() {
        val station = FavoriteStation("Test", 100.0f)
        repository.add(station)

        val list = repository.getAll()
        assertTrue(list.contains(station))
    }
}
