package com.group06.marvelapp.series.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.group06.marvelapp.R
import com.group06.marvelapp.series.model.SeriesModel

class SeriesListAdapter(private val dataset: List<SeriesModel>, private val listener: (SeriesModel) -> Unit):
    RecyclerView.Adapter<SeriesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_series_list_item, parent, false)
        return SeriesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesListViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount() = dataset.size


}