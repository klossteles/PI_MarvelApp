package com.marvelapp06.marvelapp.creator.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.creator.model.CreatorsModel
import com.squareup.picasso.Picasso

class CreatorsListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val _name = view.findViewById<TextView>(R.id.txtCreatorsName)
    private val _image = view.findViewById<ImageView>(R.id.imgCreators)

    fun bind (creatorsModel : CreatorsModel){
        _name.text = creatorsModel.fullName
        Picasso.get().load(creatorsModel.thumbnail?.getImagePath()).into(_image)
    }
}