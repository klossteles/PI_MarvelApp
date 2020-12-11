package com.group06.marvelapp.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.group06.marvelapp.R
import com.group06.marvelapp.favorite.model.SeriesFavoriteModel

class SeriesFavoriteAdapter(
    private val dataset: List<SeriesFavoriteModel>,
    private val clickListener: (SeriesFavoriteModel) -> Unit
) : RecyclerView.Adapter<SeriesFavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_series_list_item, parent, false)

        return SeriesFavoriteViewHolder(view)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: SeriesFavoriteViewHolder, position: Int) {
        val dSPosition = dataset[position]
        holder.bind(dSPosition)
        holder.itemView.setOnClickListener { clickListener(dSPosition) }
    }
}