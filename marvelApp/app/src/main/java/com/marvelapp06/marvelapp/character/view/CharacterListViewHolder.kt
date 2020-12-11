package com.marvelapp06.marvelapp.character.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.character.model.CharactersModel
import com.squareup.picasso.Picasso

class CharacterListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val _nameCharacter = view.findViewById<TextView>(R.id.txtNameCharacterItemList)
    private val _image = view.findViewById<ImageView>(R.id.imgCharacterItemList)

    fun bind(charactersModel: CharactersModel) {
        _nameCharacter.text = charactersModel.name
        Picasso.get().load(charactersModel.thumbnail?.getImagePath()).into(_image)
    }
}
