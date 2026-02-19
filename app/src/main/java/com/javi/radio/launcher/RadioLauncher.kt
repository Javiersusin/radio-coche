package com.javi.radio.launcher

import android.content.Context
import android.content.Intent

object RadioLauncher {

    /**
     * 🔴 IMPORTANTE
     * Sustituir este valor por el package real de la radio original.
     * Ejemplo:
     * const val RADIO_PACKAGE = "com.microntek.radio"
     */
    private const val RADIO_PACKAGE = "REPLACE_WITH_REAL_PACKAGE"

    fun launch(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(RADIO_PACKAGE)
        intent?.let {
            context.startActivity(it)
        }
    }
}
