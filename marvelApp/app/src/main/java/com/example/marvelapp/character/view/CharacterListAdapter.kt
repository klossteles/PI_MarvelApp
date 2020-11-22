package com.example.marvelapp.character.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.character.model.CharactersModel
import com.example.marvelapp.character.view.CharacterViewHolder

class CharacterListAdapter(
    private val dataSet: List<CharactersModel>,
    private val clickListener: (CharactersModel) -> Unit
): RecyclerView.Adapter<CharacterViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_character_list_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount()=dataSet.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{clickListener(item)}
    }
}