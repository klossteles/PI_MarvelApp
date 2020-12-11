package com.group06.marvelapp.creator.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group06.marvelapp.R
import com.group06.marvelapp.creator.model.CreatorsModel
import com.squareup.picasso.Picasso

class CreatorsListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.txtCreatorsName)
    private val image = view.findViewById<ImageView>(R.id.imgCreators)

    fun bind (creatorsModel : CreatorsModel){

        name.text = creatorsModel.fullName
        Picasso.get().load(creatorsModel.thumbnail?.getImagePath()).into(image)
    }
}