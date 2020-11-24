package com.example.marvelapp.character.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.character.model.CharactersModel
import com.squareup.picasso.Picasso

class CharacterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val nameCharacter = view.findViewById<TextView>(R.id.txtNameCharacterItemList)
    private val image = view.findViewById<ImageView>(R.id.imgCharacterItemList)

    fun bind(charactersModel: CharactersModel) {
        nameCharacter.text = charactersModel.name
        Picasso.get().load(charactersModel.thumbnail?.getImagePath()).into(imageCharacter)
    }

}
