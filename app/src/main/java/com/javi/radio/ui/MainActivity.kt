package com.javi.radio.ui

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.javi.radio.R
import com.javi.radio.data.FavoritesRepository
import com.javi.radio.databinding.ActivityMainBinding
import com.javi.radio.domain.FavoriteStation
import com.javi.radio.domain.SpanishRadioStations
import com.javi.radio.launcher.RadioLauncher

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: FavoritesRepository
    private lateinit var adapter: StationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = FavoritesRepository(this)

        adapter = StationsAdapter(
            onClick = { RadioLauncher.launch(this) },
            onDelete = { station ->
                repository.remove(station)
                loadData()
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = this@MainActivity.adapter
        }

        binding.btnAdd.setOnClickListener { showAddStationDialog() }

        loadData()
    }

    private fun loadData() {
        val stations = repository.getAll()
        adapter.submitList(stations)

        binding.stationCount.text = when (stations.size) {
            0    -> "Sin emisoras"
            1    -> "1 emisora"
            else -> "${stations.size} emisoras"
        }

        binding.emptyState.visibility = if (stations.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (stations.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun showAddStationDialog() {
        val dialog = Dialog(this, R.style.Theme_RadioApp_Dialog)
        dialog.setContentView(R.layout.dialog_add_station)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val etFrequency  = dialog.findViewById<EditText>(R.id.etFrequency)
        val etName       = dialog.findViewById<EditText>(R.id.etName)
        val tvAutoName   = dialog.findViewById<TextView>(R.id.tvAutoName)
        val btnSave      = dialog.findViewById<TextView>(R.id.btnSave)
        val btnCancel    = dialog.findViewById<TextView>(R.id.btnCancel)

        // Auto-detect station name as user types the frequency
        etFrequency.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s?.toString()?.trim() ?: ""
                val freq = text.toFloatOrNull()
                if (freq != null && freq >= 87.5f && freq <= 108.0f) {
                    val found = SpanishRadioStations.findName(freq)
                    if (found != null) {
                        tvAutoName.text = "✓ $found"
                        tvAutoName.visibility = View.VISIBLE
                        // Auto-fill name field only if user hasn't written anything yet
                        if (etName.text.isNullOrEmpty()) {
                            etName.setText(found)
                        }
                    } else {
                        tvAutoName.text = "Emisora no identificada — ponle un nombre"
                        tvAutoName.visibility = View.VISIBLE
                    }
                } else {
                    tvAutoName.visibility = View.GONE
                }
            }
        })

        btnCancel.setOnClickListener { dialog.dismiss() }

        btnSave.setOnClickListener {
            val freqText = etFrequency.text.toString().trim()
            val name     = etName.text.toString().trim()

            if (freqText.isEmpty()) {
                etFrequency.error = "Introduce una frecuencia"
                return@setOnClickListener
            }

            val frequency = freqText.toFloatOrNull()
            if (frequency == null || frequency < 87.5f || frequency > 108.0f) {
                etFrequency.error = "Rango válido: 87.5 – 108.0"
                return@setOnClickListener
            }

            val stationName = name.ifEmpty {
                SpanishRadioStations.findName(frequency) ?: "${"%.1f".format(frequency)} MHz"
            }

            repository.add(FavoriteStation(stationName, frequency))
            loadData()
            dialog.dismiss()
        }

        dialog.show()
    }
}
