package com.javi.radio.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javi.radio.databinding.ItemStationBinding
import com.javi.radio.domain.FavoriteStation

class StationsAdapter(
    private val onClick: (FavoriteStation) -> Unit
) : RecyclerView.Adapter<StationsAdapter.ViewHolder>() {

    private var items = listOf<FavoriteStation>()

    fun submitList(list: List<FavoriteStation>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(station: FavoriteStation) {
            binding.name.text = station.name
            binding.frequency.text = "${station.frequency} MHz"
            binding.root.setOnClickListener { onClick(station) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}


