package com.marvelapp06.marvelapp.favorite.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.comic.model.ComicsModel
import com.squareup.picasso.Picasso

class ComicFavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val _name = view.findViewById<TextView>(R.id.txtComicName)
    private val _image = view.findViewById<ImageView>(R.id.imgComic)

    fun bind(comic: ComicsModel) {
        _name.text = comic.title
        Picasso.get()
            .load(comic.thumbnail?.getImagePath("landscape_incredible"))
            .into(_image)
    }
}