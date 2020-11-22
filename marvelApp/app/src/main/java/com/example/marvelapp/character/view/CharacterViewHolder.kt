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
    private val imageCharacter = view.findViewById<ImageView>(R.id.imageCharacterList)

    fun bind(charactersModel: CharactersModel) {
        nameCharacter.text = charactersModel.name
        if (charactersModel.thumbnail == null || charactersModel.thumbnail.path.contains("image_not_available")) {
            Picasso.get()
                .load(R.drawable.image_not_available)
                .into(imageCharacter)
        } else {
            val imagePath="${charactersModel.thumbnail.path}/landscape_large.${charactersModel.thumbnail.extension}"
            Picasso.get()
                .load(imagePath)
                .into(imageCharacter)
        }
    }

}
