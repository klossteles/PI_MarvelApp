package com.group06.marvelapp.comic.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.group06.marvelapp.R
import com.group06.marvelapp.comic.model.ComicsModel

class ComicListAdapter (private val dataset: List<ComicsModel>, private val listener: (ComicsModel) -> Unit):
    RecyclerView.Adapter<ComicListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_comic_list_item, parent, false)
        return ComicListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicListViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount() = dataset.size
}