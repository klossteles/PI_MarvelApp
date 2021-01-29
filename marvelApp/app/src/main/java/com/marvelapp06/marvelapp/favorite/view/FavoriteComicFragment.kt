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
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.character.model.CharactersModel
import com.marvelapp06.marvelapp.comic.model.ComicsModel
import com.marvelapp06.marvelapp.comic.view.SearchComicFragment
import com.marvelapp06.marvelapp.data.model.CharacterSummary
import com.marvelapp06.marvelapp.data.model.ComicSummary
import com.marvelapp06.marvelapp.data.model.CreatorSummary
import com.marvelapp06.marvelapp.data.model.SeriesSummary
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.model.ComicFavoriteModel
import com.marvelapp06.marvelapp.favorite.repository.ComicFavoriteRepository
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.ComicFavoriteViewModel
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel

class FavoriteComicFragment : Fragment() {
    private lateinit var _view:View
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
        _view  = inflater.inflate(R.layout.fragment_favorite_comic, container, false)
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

        val currentUser = _auth.currentUser
        _viewModelFavorite.getFavoritesComics(currentUser!!.uid).observe(viewLifecycleOwner, Observer { list ->
            val listComics: MutableList<ComicsModel> = mutableListOf()
            list.forEach {
                listComics.add(jsonToObj(it.favorite))
            }
            getList(listComics)
        })
    }

    fun objToJson(comicsModel: ComicsModel):String{
        val gson = Gson()
        return gson.toJson(comicsModel)
    }

    fun characterListToJson(array: List<CharacterSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }
    fun creatorListToJson(array: List<CreatorSummary>): String {
        val gson = Gson()
        return gson.toJson(array)
    }


    fun jsonToObj(json: String): ComicsModel {
        val gson = Gson()
        return gson.fromJson(json, ComicsModel::class.java)
    }

    private fun getList(list:List<ComicsModel>){
        val viewManager= GridLayoutManager(_view.context,2)
        val recyclerView=_view.findViewById<RecyclerView>(R.id.listComicFavorites)
        val menuAdapter = ComicFavoriteAdapter(list){
            val bundle = bundleOf(
                COMIC_ID to it.id,
                COMIC_DESCRIPTION to it.description,
                COMIC_PRICE to it.prices,
                COMIC_CHARACTERS_JSON to it.characters?.items?.let { character ->characterListToJson(character) },
                COMIC_CREATORS_JSON to it.creators?.items?.let { creator -> creatorListToJson(creator) },
                COMIC_IMAGES to it.images,
                COMIC_DATES to it.dates,
                COMIC_PAGES to it.pageCount,
                COMIC_THUMBNAIL to it.thumbnail?.getImagePath("landscape_incredible"),
                COMIC_TITLE to it.title,
                COMIC_MODEL_JSON to this.objToJson(it),
                KEY_FRAGMENT to "ComicFragment"

            )
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        recyclerView.apply {
            layoutManager=viewManager
            adapter=menuAdapter
        }
    }


    companion object {
        const val COMIC_ID = "COMIC_ID"
        const val COMIC_DESCRIPTION = "COMIC_DESCRIPTION"
        const val COMIC_PRICE = "COMIC_PRICE"
        const val COMIC_IMAGES = "COMIC_IMAGES"
        const val COMIC_THUMBNAIL = "COMIC_THUMBNAIL"
        const val COMIC_PAGES = "COMIC_PAGES"
        const val COMIC_DATES = "COMIC_DATES"
        const val COMIC_TITLE = "COMIC_TITLE"
        const val COMIC_CHARACTERS_JSON = "COMIC_CHARACTERS_JSON"
        const val COMIC_CREATORS_JSON = "COMIC_CREATORS_JSON"
        const val COMIC_MODEL_JSON="COMIC_MODEL_JSON"
        const val KEY_FRAGMENT= "KEY_FRAGMENT"
    }
}
