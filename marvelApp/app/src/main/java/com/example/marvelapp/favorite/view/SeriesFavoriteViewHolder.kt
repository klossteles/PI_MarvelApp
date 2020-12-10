package com.example.marvelapp.favorite.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.SeriesFavoriteModel
import com.squareup.picasso.Picasso

class SeriesFavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.txtSeriesName)
    private val image = view.findViewById<ImageView>(R.id.imgSeries)

    fun bind(series:SeriesFavoriteModel) {
        name.text = series.name
        Picasso.get()
            .load(series.image)
            .into(image)
    }
}