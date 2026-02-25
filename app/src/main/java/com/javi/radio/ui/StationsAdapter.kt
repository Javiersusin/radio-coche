package com.javi.radio.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javi.radio.databinding.ItemStationBinding
import com.javi.radio.domain.FavoriteStation

class StationsAdapter(
    private val onClick: (FavoriteStation) -> Unit,
    private val onDelete: (FavoriteStation) -> Unit
) : RecyclerView.Adapter<StationsAdapter.ViewHolder>() {

    private var items = listOf<FavoriteStation>()
    private var editMode = false

    fun submitList(list: List<FavoriteStation>) {
        items = list
        notifyDataSetChanged()
    }

    fun setEditMode(enabled: Boolean) {
        editMode = enabled
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(station: FavoriteStation) {
            // Format frequency nicely: show one decimal place
            val freqFormatted = "%.1f".format(station.frequency)
            binding.frequency.text = freqFormatted
            binding.name.text = station.name.uppercase()

            // Show delete in edit mode
            binding.btnDelete.visibility = if (editMode) View.VISIBLE else View.GONE

            binding.cardRoot.setOnClickListener {
                if (!editMode) {
                    onClick(station)
                }
            }

            binding.cardRoot.setOnLongClickListener {
                // Toggle delete button on long press
                binding.btnDelete.visibility =
                    if (binding.btnDelete.visibility == View.VISIBLE) View.GONE
                    else View.VISIBLE
                true
            }

            binding.btnDelete.setOnClickListener {
                onDelete(station)
            }
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
