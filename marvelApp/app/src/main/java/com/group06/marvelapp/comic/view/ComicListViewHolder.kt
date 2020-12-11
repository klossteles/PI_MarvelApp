package com.group06.marvelapp.comic.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group06.marvelapp.R
import com.group06.marvelapp.comic.model.ComicsModel
import com.squareup.picasso.Picasso

class ComicListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.txtComicName)
    private val image = view.findViewById<ImageView>(R.id.imgComic)


    fun bind(comicsModel: ComicsModel) {
        name.text = comicsModel.title
        Picasso.get().load(comicsModel.thumbnail?.getImagePath()).into(image)
    }
}