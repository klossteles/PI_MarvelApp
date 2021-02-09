package com.marvelapp06.marvelapp.favorite.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.character.model.CharactersModel
import com.marvelapp06.marvelapp.data.model.ComicSummary
import com.marvelapp06.marvelapp.data.model.SeriesSummary
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.adapter.CharacterFavoriteAdapter
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel

class FavoriteCharacterFragment : Fragment() {
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
        _view = inflater.inflate(R.layout.fragment_favorite_character, container, false)
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

        getFavoritesCharacters()
    }

    override fun onResume() {
        super.onResume()
        getFavoritesCharacters()
    }

    private fun getFavoritesCharacters() {
        val currentUser = _auth.currentUser
        _viewModelFavorite.getFavoritesCharacters(currentUser!!.uid)
            .observe(viewLifecycleOwner, Observer { list ->
                val listCharacters: MutableList<CharactersModel> = mutableListOf()
                    list.forEach {
                        listCharacters.add(jsonToObj(it.favorite))
                }
                getList(listCharacters)
                notFoundFavorite(listCharacters.isEmpty())
            })
    }


    private fun notFoundFavorite(notFound: Boolean) {
        if (notFound) {
            _view.findViewById<View>(R.id.noFavoriteCharacters).visibility = View.VISIBLE
        } else {
            _view.findViewById<View>(R.id.noFavoriteCharacters).visibility = View.GONE
        }
    }

    private fun getList(list: List<CharactersModel>) {
        val viewManager = GridLayoutManager(_view.context, 2)
        val recyclerView = _view.findViewById<RecyclerView>(R.id.listCharactersFavorites)
        val menuAdapter =
            CharacterFavoriteAdapter(
                list
            ) {
                val bundle = bundleOf(
                    CHARACTER_ID to it.id,
                    CHARACTER_NAME to it.name,
                    CHARACTER_DESCRIPTION to it.description,
                    CHARACTER_COMIC_JSON to it.comics?.items?.let { comic -> comicListToJson(comic) },
                    CHARACTER_SERIES_JSON to it.series?.items?.let { serie -> serieListToJson(serie) },
                    CHARACTER_THUMBNAIL to it.thumbnail?.getImagePath("landscape_incredible"),
                    KEY_FRAGMENT to "CharacterFragment",
                    CHARACTER_MODEL_JSON to this.objToJson(it)
                )

                val intent = Intent(context, MainActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = menuAdapter
        }
    }


    private fun objToJson(charactersModel: CharactersModel):String{
        val gson = Gson()
        return gson.toJson(charactersModel)
    }

    private fun comicListToJson(array: List<ComicSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }
    private fun serieListToJson(array: List<SeriesSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }

    private fun jsonToObj(json: String): CharactersModel {
        val gson = Gson()
        return gson.fromJson(json, CharactersModel::class.java)
    }



    companion object {
        const val CHARACTER_ID = "CHARACTER_ID"
        const val CHARACTER_NAME = "CHARACTER_NAME"
        const val CHARACTER_DESCRIPTION = "CHARACTER_DESCRIPTION"
        const val CHARACTER_COMIC_JSON = "CHARACTER_COMIC_JSON"
        const val CHARACTER_SERIES_JSON = "CHARACTER_SERIES_JSON"
        const val CHARACTER_THUMBNAIL = "CHARACTER_THUMBNAIL"
        const val KEY_FRAGMENT= "KEY_FRAGMENT"
        const val CHARACTER_MODEL_JSON="CHARACTER_MODEL_JSON"
    }
}