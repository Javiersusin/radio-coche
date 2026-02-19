package com.javi.radio

import com.javi.radio.domain.FavoriteStation
import org.junit.Assert.*
import org.junit.Test

class FavoriteStationTest {

    @Test
    fun createStation_correctValues() {
        val station = FavoriteStation("Rock FM", 95.2f)

        assertEquals("Rock FM", station.name)
        assertEquals(95.2f, station.frequency)
    }
}
