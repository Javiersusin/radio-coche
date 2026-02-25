package com.javi.radio.domain

/**
 * Base de datos de emisoras españolas.
 * Prioriza frecuencias de Zaragoza / Huesca / Aragón.
 */
object SpanishRadioStations {

    // Frecuencias para Zaragoza y Huesca (Aragón)
    // Fuente: listados oficiales y verificados manualmente
    private val stations: Map<Float, String> = mapOf(

        // ─── ARAGÓN / ZARAGOZA / HUESCA ──────────────────────────────
        // Aragón Radio / Aragón Deportes (Aragón Radio 2)
        93.9f  to "Aragón Deportes",   // Zaragoza
        99.5f  to "Aragón Deportes",   // Huesca
        100.9f to "Aragón Radio",      // Zaragoza
        96.5f  to "Aragón Radio",      // Huesca

        // Radio Ebro (local Zaragoza)
        97.7f  to "Radio Ebro",
        106.3f to "Radio Ebro",

        // Cadena 100
        97.2f  to "Cadena 100",        // Zaragoza
        104.5f to "Cadena 100",        // Huesca

        // Europa FM
        88.2f  to "Europa FM",         // Zaragoza
        101.4f to "Europa FM",         // Huesca

        // Hit FM
        91.5f  to "Hit FM",            // Zaragoza
        105.2f to "Hit FM",            // Huesca

        // COPE
        95.0f  to "COPE",              // Zaragoza
        100.0f to "COPE",              // Huesca
        103.8f to "COPE",              // área Aragón

        // ─── NACIONALES COMUNES ───────────────────────────────────────

        // Cadena SER
        92.0f  to "Cadena SER",        // Zaragoza
        94.8f  to "Cadena SER",        // Huesca
        103.4f to "Cadena SER",

        // Los 40
        87.9f  to "Los 40",
        93.3f  to "Los 40",            // Zaragoza
        98.7f  to "Los 40",            // Huesca

        // Onda Cero
        90.7f  to "Onda Cero",         // Zaragoza
        96.0f  to "Onda Cero",         // Huesca
        102.5f to "Onda Cero",

        // M80
        89.5f  to "M80",
        95.8f  to "M80",              // Zaragoza
        102.1f to "M80",

        // Rock FM
        91.0f  to "Rock FM",
        98.0f  to "Rock FM",          // Zaragoza
        105.7f to "Rock FM",

        // Máxima FM
        89.1f  to "Máxima FM",
        96.3f  to "Máxima FM",        // Zaragoza
        103.1f to "Máxima FM",

        // Cadena Dial
        90.3f  to "Cadena Dial",
        97.0f  to "Cadena Dial",      // Zaragoza
        103.7f to "Cadena Dial",

        // Kiss FM
        92.7f  to "Kiss FM",
        99.3f  to "Kiss FM",          // Zaragoza
        105.4f to "Kiss FM",

        // RNE Radio 1
        93.6f  to "RNE Radio 1",
        99.1f  to "RNE Radio 1",      // Zaragoza
        101.7f to "RNE Radio 1",      // Huesca

        // RNE Radio 3
        100.5f to "RNE Radio 3",

        // RNE Clásica
        104.1f to "RNE Clásica",

        // Mega (Atresmedia)
        88.8f  to "Mega",
        94.2f  to "Mega",

        // Es Radio
        107.3f to "EsRadio",

        // Radio María
        106.8f to "Radio María",

        // Hit FM extra
        107.0f to "Hit FM"
    )

    /**
     * Dado una frecuencia, devuelve el nombre de la emisora si se conoce.
     * Usa tolerancia ±0.1 MHz para cubrir redondeos.
     */
    fun findName(frequency: Float): String? {
        val rounded = roundTo1Decimal(frequency)
        // Búsqueda exacta
        stations[rounded]?.let { return it }
        // Búsqueda con tolerancia
        return stations.entries
            .firstOrNull { (f, _) -> kotlin.math.abs(f - frequency) <= 0.1f }
            ?.value
    }

    private fun roundTo1Decimal(v: Float): Float =
        (Math.round(v * 10) / 10.0f)
}
