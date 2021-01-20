package com.marvelapp06.marvelapp.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.character.model.CharactersModel
import com.marvelapp06.marvelapp.favorite.model.CharacterFavoriteModel

class CharacterFavoriteAdapter(
    private val _dataset: List<CharactersModel>,
    private val _clickListener: (CharactersModel) ->Unit
):RecyclerView.Adapter<CharacterFavoriteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_character_list_item,parent,false)

        return CharacterFavoriteViewHolder(view)
    }

    override fun getItemCount()=_dataset.size

    override fun onBindViewHolder(holder: CharacterFavoriteViewHolder, position: Int) {
        val dSPosition = _dataset[position]
        holder.bind(dSPosition)
        holder.itemView.setOnClickListener{_clickListener(dSPosition)}
    }

}