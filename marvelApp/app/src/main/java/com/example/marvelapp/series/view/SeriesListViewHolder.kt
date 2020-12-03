package com.example.marvelapp.series.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.series.model.SeriesModel
import com.squareup.picasso.Picasso

class SeriesListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.txtSeriesName)
    private val image = view.findViewById<ImageView>(R.id.imgSeries)

    fun bind(seriesModel: SeriesModel) {
        name.text = seriesModel.title
        Picasso.get().load(seriesModel.thumbnail?.getImagePath()).into(image)
    }
}