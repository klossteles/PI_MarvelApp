package com.marvelapp06.marvelapp.favorite.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.model.CharacterFavoriteModel
import com.squareup.picasso.Picasso

class CharacterFavoriteViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val _nameCharacter = view.findViewById<TextView>(R.id.txtNameCharacterItemList)
    private val _imageCharacter = view.findViewById<ImageView>(R.id.imgCharacterItemList)

    fun bind(character : CharacterFavoriteModel){
        _nameCharacter.text=character.name
        Picasso.get()
            .load(character.image)
            .into(_imageCharacter)
    }
}
