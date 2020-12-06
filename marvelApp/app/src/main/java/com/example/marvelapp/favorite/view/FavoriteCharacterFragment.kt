package com.example.marvelapp.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.favorite.model.CharacterFavoriteModel
import com.example.marvelapp.favorite.repository.CharacterFavoriteRepository
import com.example.marvelapp.favorite.viewmodel.CharacterFavoriteViewModel

class FavoriteCharacterFragment : Fragment() {
    private lateinit var _view:View
    private lateinit var _viewModel:CharacterFavoriteViewModel
    private lateinit var _navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view= inflater.inflate(R.layout.fragment_favorite_character, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = ViewModelProvider(
            this,
            CharacterFavoriteViewModel.CharacterFavoriteViewModelFactory(CharacterFavoriteRepository(_view.context))
        ).get(CharacterFavoriteViewModel::class.java)

        _viewModel.listCharactersFavoriteData.observe(viewLifecycleOwner,Observer{
            getList(it)
        })

        _viewModel.getCharactersFavorites()
    }

    fun getList(list:List<CharacterFavoriteModel>){
        val viewManager=GridLayoutManager(_view.context,2)
        val recyclerView=_view.findViewById<RecyclerView>(R.id.listCharactersFavorites)
        val menuAdapter = CharacterFavoriteAdapter(list){
            Toast.makeText(context, "Teste", Toast.LENGTH_SHORT).show()
        }

        recyclerView.apply {
            layoutManager=viewManager
            adapter=menuAdapter
        }
    }



}