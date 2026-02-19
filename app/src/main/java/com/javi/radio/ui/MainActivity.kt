package com.javi.radio.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.javi.radio.data.FavoritesRepository
import com.javi.radio.databinding.ActivityMainBinding
import com.javi.radio.domain.FavoriteStation
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

        adapter = StationsAdapter { station ->
            RadioLauncher.launch(this)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        loadData()
    }

    private fun loadData() {
        adapter.submitList(repository.getAll())
    }
}
