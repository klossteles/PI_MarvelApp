package com.marvelapp06.marvelapp.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.model.CreatorsFavoriteModel
import com.marvelapp06.marvelapp.favorite.repository.CreatorsFavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.CreatorsFavoriteViewModel

class FavoriteCreatorFragment : Fragment() {
    private lateinit var _view:View
    private lateinit var _viewModel: CreatorsFavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view  = inflater.inflate(R.layout.fragment_favorite_comic, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = ViewModelProvider(
            this,
            CreatorsFavoriteViewModel.CreatorsFavoriteViewModelFactory(CreatorsFavoriteRepository(_view.context))
        ).get(CreatorsFavoriteViewModel::class.java)

        _viewModel.listCreatorsFavoriteData.observe(viewLifecycleOwner, Observer{
            getList(it)
        })

        _viewModel.getCreatorsFavorites()
    }

    private fun getList(list:List<CreatorsFavoriteModel>){
        val viewManager= GridLayoutManager(_view.context,2)
        val recyclerView=_view.findViewById<RecyclerView>(R.id.listComicFavorites)
        val menuAdapter = CreatorsFavoriteAdapter(list){
            Toast.makeText(context, "clicado", Toast.LENGTH_SHORT).show()
        }

        recyclerView.apply {
            layoutManager=viewManager
            adapter=menuAdapter
        }
    }
}