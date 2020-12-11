package com.marvelapp06.marvelapp.character.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.character.model.CharactersModel

class CharacterListAdapter(
    private val _dataSet: List<CharactersModel>,
    private val _clickListener: (CharactersModel) -> Unit
): RecyclerView.Adapter<CharacterListViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_character_list_item, parent, false)
        return CharacterListViewHolder(view)
    }

    override fun getItemCount()=_dataSet.size

    override fun onBindViewHolder(holderList: CharacterListViewHolder, position: Int) {
        val item = _dataSet[position]
        holderList.bind(item)
        holderList.itemView.setOnClickListener{_clickListener(item)}
    }
}