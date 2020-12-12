package com.marvelapp06.marvelapp.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.model.ComicFavoriteModel

class ComicFavoriteAdapter(
    private val _dataset: List<ComicFavoriteModel>,
    private val _clickListener: (ComicFavoriteModel) -> Unit
) : RecyclerView.Adapter<ComicFavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_comic_list_item, parent, false)

        return ComicFavoriteViewHolder(view)
    }

    override fun getItemCount() = _dataset.size

    override fun onBindViewHolder(holder: ComicFavoriteViewHolder, position: Int) {
        val dSPosition = _dataset[position]
        holder.bind(dSPosition)
        holder.itemView.setOnClickListener { _clickListener(dSPosition) }
    }
}