package com.marvelapp06.marvelapp.comic.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.comic.model.ComicsModel
import com.squareup.picasso.Picasso

class ComicListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val _name = view.findViewById<TextView>(R.id.txtComicName)
    private val _image = view.findViewById<ImageView>(R.id.imgComic)

    fun bind(comicsModel: ComicsModel) {
        _name.text = comicsModel.title
        Picasso.get().load(comicsModel.thumbnail?.getImagePath()).into(_image)
    }
}