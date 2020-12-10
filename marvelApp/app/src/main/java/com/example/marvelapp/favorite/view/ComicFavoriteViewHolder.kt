package com.example.marvelapp.favorite.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.ComicFavoriteModel
import com.squareup.picasso.Picasso

class ComicFavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.txtComicName)
    private val image = view.findViewById<ImageView>(R.id.imgComic)

    fun bind(comic: ComicFavoriteModel) {
        name.text = comic.name
        Picasso.get()
            .load(comic.image)
            .into(image)
    }
}