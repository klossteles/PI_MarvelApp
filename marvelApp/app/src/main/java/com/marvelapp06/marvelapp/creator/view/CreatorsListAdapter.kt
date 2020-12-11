package com.marvelapp06.marvelapp.creator.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.creator.model.CreatorsModel

class CreatorsListAdapter(
    private val _dataset: List<CreatorsModel>,
    private val _listener: (CreatorsModel) -> Unit
) :
    RecyclerView.Adapter<CreatorsListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_creators_item_list, parent, false)
        return CreatorsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreatorsListViewHolder, position: Int) {
        val item = _dataset[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { _listener(item) }
    }

    override fun getItemCount()= _dataset.size
}