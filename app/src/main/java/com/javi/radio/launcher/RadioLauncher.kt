package com.javi.radio.launcher

import android.content.Context
import android.content.Intent
import android.widget.Toast

object RadioLauncher {

    private const val RADIO_PACKAGE = "com.jancar.radio"

    fun launch(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(RADIO_PACKAGE)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } else {
            Toast.makeText(
                context,
                "No se pudo abrir la radio del sistema",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
