package com.marvelapp06.marvelapp.favorite.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.model.SeriesFavoriteModel
import com.squareup.picasso.Picasso

class SeriesFavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val _name = view.findViewById<TextView>(R.id.txtSeriesName)
    private val _image = view.findViewById<ImageView>(R.id.imgSeries)

    fun bind(series:SeriesFavoriteModel) {
        _name.text = series.name
        Picasso.get()
            .load(series.image)
            .into(_image)
    }
}