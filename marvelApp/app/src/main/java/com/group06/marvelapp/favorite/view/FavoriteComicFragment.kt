package com.group06.marvelapp.favorite.view

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
import com.group06.marvelapp.R
import com.group06.marvelapp.favorite.model.ComicFavoriteModel
import com.group06.marvelapp.favorite.repository.ComicFavoriteRepository
import com.group06.marvelapp.favorite.viewmodel.ComicFavoriteViewModel

class FavoriteComicFragment : Fragment() {

    private lateinit var _view:View
    private lateinit var _viewModel: ComicFavoriteViewModel

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
            ComicFavoriteViewModel.ComicFavoriteViewModelFactory(ComicFavoriteRepository(_view.context))
        ).get(ComicFavoriteViewModel::class.java)

        _viewModel.listComicFavoriteData.observe(viewLifecycleOwner, Observer{
            getList(it)
        })

        _viewModel.getComicFavorites()
    }


    private fun getList(list:List<ComicFavoriteModel>){
        val viewManager= GridLayoutManager(_view.context,2)
        val recyclerView=_view.findViewById<RecyclerView>(R.id.listComicFavorites)
        val menuAdapter = ComicFavoriteAdapter(list){
            Toast.makeText(context, "clicado", Toast.LENGTH_SHORT).show()
        }

        recyclerView.apply {
            layoutManager=viewManager
            adapter=menuAdapter
        }
    }
}
