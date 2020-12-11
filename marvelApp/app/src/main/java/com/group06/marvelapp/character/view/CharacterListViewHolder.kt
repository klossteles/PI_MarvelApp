package com.group06.marvelapp.character.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group06.marvelapp.R
import com.group06.marvelapp.character.model.CharactersModel
import com.squareup.picasso.Picasso

class CharacterListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val nameCharacter = view.findViewById<TextView>(R.id.txtNameCharacterItemList)
    private val image = view.findViewById<ImageView>(R.id.imgCharacterItemList)

    fun bind(charactersModel: CharactersModel) {
        nameCharacter.text = charactersModel.name
        Picasso.get().load(charactersModel.thumbnail?.getImagePath()).into(image)
    }
}
