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
import com.group06.marvelapp.favorite.model.SeriesFavoriteModel
import com.group06.marvelapp.favorite.repository.SeriesFavoriteRepository
import com.group06.marvelapp.favorite.viewmodel.SeriesFavoriteViewModel

class FavoriteSeriesFragment : Fragment() {

    private lateinit var _view:View
    private lateinit var _viewModel: SeriesFavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view =inflater.inflate(R.layout.fragment_favorite_series, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = ViewModelProvider(
            this,
            SeriesFavoriteViewModel.SeriesFavoriteViewModelFactory(SeriesFavoriteRepository(_view.context))
        ).get(SeriesFavoriteViewModel::class.java)

        _viewModel.listSeriesFavoriteData.observe(viewLifecycleOwner, Observer{
            getList(it)
        })

        _viewModel.getSeriesFavorites()

    }

    fun getList(list:List<SeriesFavoriteModel>){
        val viewManager= GridLayoutManager(_view.context,2)
        val recyclerView=_view.findViewById<RecyclerView>(R.id.listSeriesFavorites)
        val menuAdapter = SeriesFavoriteAdapter(list){
            Toast.makeText(context, "redirecionar para serie em questão", Toast.LENGTH_SHORT).show()
        }

        recyclerView.apply {
            layoutManager=viewManager
            adapter=menuAdapter
        }
    }

}