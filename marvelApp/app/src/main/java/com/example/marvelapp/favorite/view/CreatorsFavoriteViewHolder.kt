package com.example.marvelapp.favorite.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.ComicFavoriteModel
import com.example.marvelapp.favorite.model.CreatorsFavoriteModel
import com.squareup.picasso.Picasso

class CreatorsFavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.txtCreatorsName)
    private val image = view.findViewById<ImageView>(R.id.imgCreators)

    fun bind(creator:CreatorsFavoriteModel) {
        name.text = creator.name
        Picasso.get()
            .load(creator.image)
            .into(image)
    }
}