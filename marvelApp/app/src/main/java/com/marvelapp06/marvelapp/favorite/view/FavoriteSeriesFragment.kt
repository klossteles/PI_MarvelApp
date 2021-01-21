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
import com.google.gson.Gson
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.comic.model.ComicsModel
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.model.SeriesFavoriteModel
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.repository.SeriesFavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel
import com.marvelapp06.marvelapp.favorite.viewmodel.SeriesFavoriteViewModel
import com.marvelapp06.marvelapp.series.model.SeriesModel

class FavoriteSeriesFragment : Fragment() {

    private lateinit var _view:View
    private lateinit var _viewModelFavorite: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view =inflater.inflate(R.layout.fragment_favorite_series, container, false)
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModelFavorite = ViewModelProvider(
            this,
            FavoriteViewModel.FavoriteViewModelFactory(
                FavoriteRepository(
                    AppDatabase.getDatabase(
                        _view.context
                    ).favoriteDao()
                )
            )
        ).get(FavoriteViewModel::class.java)

        _viewModelFavorite.getFavoritesSeries().observe(viewLifecycleOwner, Observer { list ->
            val listSeries: MutableList<SeriesModel> = mutableListOf()
            list.forEach {
                listSeries.add(jsonToObj(it.favorite))
            }
            getList(listSeries)
        })

    }

    fun objToJson(seriesModel: SeriesModel): String {
        val gson = Gson()
        return gson.toJson(seriesModel)
    }

    fun jsonToObj(json: String): SeriesModel {
        val gson = Gson()
        return gson.fromJson(json, SeriesModel::class.java)
    }

    fun getList(list:List<SeriesModel>){
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