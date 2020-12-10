package com.example.marvelapp.favorite.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.CharacterFavoriteModel

class CharacterFavoriteAdapter(
    private val dataset: List<CharacterFavoriteModel>,
    private val clickListener: (CharacterFavoriteModel) ->Unit
):RecyclerView.Adapter<CharacterFavoriteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_character_list_item,parent,false)

        return CharacterFavoriteViewHolder(view)
    }

    override fun getItemCount()=dataset.size

    override fun onBindViewHolder(holder: CharacterFavoriteViewHolder, position: Int) {
        val dSPosition = dataset[position]
        holder.bind(dSPosition)
        holder.itemView.setOnClickListener{clickListener(dSPosition)}
    }

}