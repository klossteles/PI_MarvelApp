package com.marvelapp06.marvelapp.favorite.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.marvelapp06.marvelapp.creator.model.CreatorsModel
import com.marvelapp06.marvelapp.data.model.ComicSummary
import com.marvelapp06.marvelapp.data.model.EventSummary
import com.marvelapp06.marvelapp.data.model.SeriesSummary
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.model.CreatorsFavoriteModel
import com.marvelapp06.marvelapp.favorite.repository.CreatorsFavoriteRepository
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.CreatorsFavoriteViewModel
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel

class FavoriteCreatorFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _viewModelFavorite: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view = inflater.inflate(R.layout.fragment_favorite_comic, container, false)
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


        _viewModelFavorite.getFavoritesCreators().observe(viewLifecycleOwner, Observer { list ->
            val listCreators: MutableList<CreatorsModel> = mutableListOf()
            list.forEach {
                listCreators.add(jsonToObj(it.favorite))
            }
            getList(listCreators)
        })

    }

    private fun getList(list: List<CreatorsModel>) {
        val viewManager = GridLayoutManager(_view.context, 2)
        val recyclerView = _view.findViewById<RecyclerView>(R.id.listComicFavorites)
        val menuAdapter = CreatorsFavoriteAdapter(list) {
            val bundle = bundleOf(
                CREATORS_ID to it.id,
                CREATORS_THUMBNAIL to it.thumbnail?.getImagePath("landscape_incredible"),
                CREATORS_FULLNAME to it.fullName,
                CREATORS_SERIES_JSON to it.series?.items?.let { serie -> serieListToJson(serie) },
                CREATORS_COMICS_JSON to it.comics?.items?.let { comic -> comicListToJson(comic) },
                CREATORS_EVENTS_JSON to it.events?.items?.let { event -> eventListToJson(event) },
                KEY_FRAGMENT to "CreatorsFragment",
                CREATORS_MODEL_JSON to this.objToJson(it)
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

    fun jsonToObj(json: String): CreatorsModel {
        val gson = Gson()
        return gson.fromJson(json, CreatorsModel::class.java)
    }

    fun objToJson(creatorsModel: CreatorsModel): String {
        val gson = Gson()
        return gson.toJson(creatorsModel)
    }

    fun comicListToJson(array: List<ComicSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    fun serieListToJson(array: List<SeriesSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    fun eventListToJson(array: List<EventSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    companion object {

        const val CREATORS_ID = "CREATORS_ID"
        const val CREATORS_THUMBNAIL = "CREATORS_THUMBNAIL"
        const val CREATORS_FULLNAME = "CREATORS_FULLNAME"
        const val CREATORS_SERIES_JSON = "CREATORS_SERIES_JSON"
        const val CREATORS_COMICS_JSON = "CREATORS_COMICS_JSON"
        const val CREATORS_EVENTS_JSON = "CREATORS_EVENTS_JSON"
        const val CREATORS_MODEL_JSON = "CREATORS_MODEL_JSON"
        const val KEY_FRAGMENT = "KEY_FRAGMENT"

    }
}