package com.group06.marvelapp.character.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.group06.marvelapp.R
import com.group06.marvelapp.character.model.CharactersModel

class CharacterListAdapter(
    private val dataSet: List<CharactersModel>,
    private val clickListener: (CharactersModel) -> Unit
): RecyclerView.Adapter<CharacterListViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_character_list_item, parent, false)
        return CharacterListViewHolder(view)
    }

    override fun getItemCount()=dataSet.size

    override fun onBindViewHolder(holderList: CharacterListViewHolder, position: Int) {
        val item = dataSet[position]
        holderList.bind(item)
        holderList.itemView.setOnClickListener{clickListener(item)}
    }
}