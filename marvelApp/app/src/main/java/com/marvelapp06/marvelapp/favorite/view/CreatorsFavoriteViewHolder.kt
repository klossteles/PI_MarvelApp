package com.marvelapp06.marvelapp.favorite.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.model.CreatorsFavoriteModel
import com.squareup.picasso.Picasso

class CreatorsFavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val _name = view.findViewById<TextView>(R.id.txtCreatorsName)
    private val _image = view.findViewById<ImageView>(R.id.imgCreators)

    fun bind(creator:CreatorsFavoriteModel) {
        _name.text = creator.name
        Picasso.get()
            .load(creator.image)
            .into(_image)
    }
}