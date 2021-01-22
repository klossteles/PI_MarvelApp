package com.marvelapp06.marvelapp.favorite.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.comic.model.ComicsModel
import com.marvelapp06.marvelapp.data.model.CharacterSummary
import com.marvelapp06.marvelapp.data.model.ComicSummary
import com.marvelapp06.marvelapp.data.model.CreatorSummary
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.model.SeriesFavoriteModel
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.repository.SeriesFavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel
import com.marvelapp06.marvelapp.favorite.viewmodel.SeriesFavoriteViewModel
import com.marvelapp06.marvelapp.series.model.SeriesModel
import com.marvelapp06.marvelapp.series.view.SeriesListFragment

class FavoriteSeriesFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _viewModelFavorite: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view = inflater.inflate(R.layout.fragment_favorite_series, container, false)
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

    fun charactersListToJson(array: List<CharacterSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    fun comicListToJson(array: List<ComicSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    fun creatorsListToJson(array: List<CreatorSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    fun getList(list: List<SeriesModel>) {
        val viewManager = GridLayoutManager(_view.context, 2)
        val recyclerView = _view.findViewById<RecyclerView>(R.id.listSeriesFavorites)
        val menuAdapter = SeriesFavoriteAdapter(list) {
            val bundle = bundleOf(
                SERIES_ID to it.id,
                SERIES_TITLE to it.title,
                SERIES_CHARACTERS_JSON to it.characters?.items?.let { characters ->
                    charactersListToJson(
                        characters
                    )
                },
                SERIES_COMICS_JSON to it.comics?.items?.let { comic -> comicListToJson(comic) },
                SERIES_CREATORS_JSON to it.creators?.items?.let { creators ->
                    creatorsListToJson(
                        creators
                    )
                },
                SERIES_DESCRIPTION to it.description,
                SERIES_THUMBNAIL to it.thumbnail?.getImagePath("landscape_incredible"),
                SERIES_START to it.startYear,
                SERIES_END to it.endYear,
                KEY_FRAGMENT to "SeriesFragment",
                SERIES_MODEL_JSON to this.objToJson(it)
            )

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        recyclerView.apply {
            layoutManager = viewManager
            adapter = menuAdapter
        }
    }

    companion object {
        const val SERIES_ID = "SERIES_ID"
        const val SERIES_TITLE = "SERIES_TITLE"
        const val SERIES_DESCRIPTION = "SERIES_DESCRIPTION"
        const val SERIES_CREATORS_JSON = "SERIES_CREATORS_JSON"
        const val SERIES_CHARACTERS_JSON = "SERIES_CHARACTERS_JSON"
        const val SERIES_COMICS_JSON = "SERIES_COMICS_JSON"
        const val SERIES_THUMBNAIL = "SERIES_THUMBNAIL"
        const val SERIES_START = "SERIES_START"
        const val SERIES_END = "SERIES_END"
        const val SERIES_MODEL_JSON = "SERIES_MODEL_JSON"
        const val KEY_FRAGMENT = "KEY_FRAGMENT"
    }

}