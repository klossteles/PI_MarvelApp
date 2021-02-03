package com.marvelapp06.marvelapp.favorite.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.character.model.CharactersModel
import com.squareup.picasso.Picasso

class CharacterFavoriteViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val _nameCharacter = view.findViewById<TextView>(R.id.txtNameCharacterItemList)
    private val _imageCharacter = view.findViewById<ImageView>(R.id.imgCharacterItemList)

    fun bind(character : CharactersModel){
        _nameCharacter.text=character.name
        Picasso.get()
            .load(character.thumbnail?.getImagePath("landscape_incredible"))
            .into(_imageCharacter)
    }
}
