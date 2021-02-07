package com.marvelapp06.marvelapp.favorite.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.series.model.SeriesModel
import com.squareup.picasso.Picasso

class SeriesFavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val _name = view.findViewById<TextView>(R.id.txtSeriesName)
    private val _image = view.findViewById<ImageView>(R.id.imgSeries)

    fun bind(series: SeriesModel) {
        _name.text = series.title
        Picasso.get()
            .load(series.thumbnail?.getImagePath("landscape_incredible"))
            .into(_image)
    }
}