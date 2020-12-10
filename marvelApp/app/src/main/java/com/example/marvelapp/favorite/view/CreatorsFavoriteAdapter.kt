package com.example.marvelapp.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.CreatorsFavoriteModel

class CreatorsFavoriteAdapter(
    private val dataSet: List<CreatorsFavoriteModel>,
    private val clickListener: (CreatorsFavoriteModel) -> Unit
) : RecyclerView.Adapter<CreatorsFavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorsFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_creators_item_list, parent, false)

        return CreatorsFavoriteViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CreatorsFavoriteViewHolder, position: Int) {
        val dSPosition = dataSet[position]
        holder.bind(dSPosition)
        holder.itemView.setOnClickListener { clickListener(dSPosition) }
    }
}