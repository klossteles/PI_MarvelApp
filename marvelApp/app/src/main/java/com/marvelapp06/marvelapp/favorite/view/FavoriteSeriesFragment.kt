package com.marvelapp06.marvelapp.favorite.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.data.model.CharacterSummary
import com.marvelapp06.marvelapp.data.model.ComicSummary
import com.marvelapp06.marvelapp.data.model.CreatorSummary
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.adapter.SeriesFavoriteAdapter
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel
import com.marvelapp06.marvelapp.series.model.SeriesModel

class FavoriteSeriesFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _viewModelFavorite: FavoriteViewModel
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _auth = FirebaseAuth.getInstance()
    }


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


        getFavoritesSeries()
    }

    override fun onResume() {
        super.onResume()
        getFavoritesSeries()
    }

    private fun getFavoritesSeries() {
        val currentUser = _auth.currentUser
        _viewModelFavorite.getFavoritesSeries(currentUser!!.uid)
            .observe(viewLifecycleOwner, Observer { list ->
                val listSeries: MutableList<SeriesModel> = mutableListOf()
                list.forEach {
                    listSeries.add(jsonToObj(it.favorite))
                }
                getList(listSeries)
                notFoundFavorite(listSeries.isEmpty())
            })
    }

    private fun notFoundFavorite(notFound: Boolean) {
        if (notFound) {
            _view.findViewById<View>(R.id.noFavoriteSeries).visibility = View.VISIBLE
        } else {
            _view.findViewById<View>(R.id.noFavoriteSeries).visibility = View.GONE
        }
    }

    private fun objToJson(seriesModel: SeriesModel): String {
        val gson = Gson()
        return gson.toJson(seriesModel)
    }

    private fun jsonToObj(json: String): SeriesModel {
        val gson = Gson()
        return gson.fromJson(json, SeriesModel::class.java)
    }

    private fun charactersListToJson(array: List<CharacterSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    private fun comicListToJson(array: List<ComicSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    private fun creatorsListToJson(array: List<CreatorSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }


    private fun getList(list: List<SeriesModel>) {
        val viewManager = GridLayoutManager(_view.context, 2)
        val recyclerView = _view.findViewById<RecyclerView>(R.id.listSeriesFavorites)
        val menuAdapter =
            SeriesFavoriteAdapter(list) {
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