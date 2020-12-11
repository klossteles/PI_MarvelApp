package com.marvelapp06.marvelapp.series.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.series.model.SeriesModel

class SeriesListAdapter(private val _dataset: List<SeriesModel>, private val _listener: (SeriesModel) -> Unit):
    RecyclerView.Adapter<SeriesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_series_list_item, parent, false)
        return SeriesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesListViewHolder, position: Int) {
        val item = _dataset[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { _listener(item) }
    }

    override fun getItemCount() = _dataset.size


}