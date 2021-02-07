package com.marvelapp06.marvelapp.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.creator.model.CreatorsModel

class CreatorsFavoriteAdapter(
    private val _dataSet: List<CreatorsModel>,
    private val _clickListener: (CreatorsModel) -> Unit
) : RecyclerView.Adapter<CreatorsFavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorsFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_creators_item_list, parent, false)

        return CreatorsFavoriteViewHolder(
            view
        )
    }

    override fun getItemCount() = _dataSet.size

    override fun onBindViewHolder(holder: CreatorsFavoriteViewHolder, position: Int) {
        val dSPosition = _dataSet[position]
        holder.bind(dSPosition)
        holder.itemView.setOnClickListener { _clickListener(dSPosition) }
    }
}