package com.marvelapp06.marvelapp.character.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marvelapp06.marvelapp.LoginActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.character.repository.CharacterRepository
import com.marvelapp06.marvelapp.character.viewmodel.CharacterViewModel
import com.marvelapp06.marvelapp.data.model.ComicSummary
import com.marvelapp06.marvelapp.data.model.SeriesSummary
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel
import com.marvelapp06.marvelapp.login.view.LoginFragment
import com.squareup.picasso.Picasso

class CharacterFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _viewModel: CharacterViewModel
    private lateinit var _viewModelFavorite: FavoriteViewModel
    private lateinit var _auth: FirebaseAuth
    private var _idCharacter: Int? = null
    private lateinit var _characterModelJson: String
    private var isFavorite: Boolean = false
    private var color: Int? = null
    private lateinit var charactersFavorites: ImageView
    private  var _user:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        charactersFavorites = _view.findViewById<ImageView>(R.id.imgCharactersDetailsFavorite)
        _viewModel = ViewModelProvider(
            this,
            CharacterViewModel.CharacterViewModelFactory(CharacterRepository())
        ).get(CharacterViewModel::class.java)

        val txtCharacterDetailsName = _view.findViewById<TextView>(R.id.txtCharacterName)
        val image = _view.findViewById<ImageView>(R.id.imageCharacter)
        val txtDescription = _view.findViewById<TextView>(R.id.txtDescriptionCharacters)
        val cgComics = _view.findViewById<ChipGroup>(R.id.cgComicsCharacters)
        val cgSeries = _view.findViewById<ChipGroup>(R.id.cgSeriesCharacters)

        _idCharacter = arguments?.getInt(CharacterListFragment.CHARACTER_ID)
        val name = arguments?.getString(CharacterListFragment.CHARACTER_NAME)
        val thumbnail = arguments?.getString(CharacterListFragment.CHARACTER_THUMBNAIL)
        val description = arguments?.getString(CharacterListFragment.CHARACTER_DESCRIPTION)
        var comics: Any
        var series: Any

        if (arguments?.get(CharacterListFragment.CHARACTER_COMIC) == null) {
            comics = jsonToObjComics(arguments?.getString("CHARACTER_COMIC_JSON")!!)
        } else {
            comics = arguments?.get(CharacterListFragment.CHARACTER_COMIC)!!
        }

        if (arguments?.get(CharacterListFragment.CHARACTER_SERIES) == null) {
            series = jsonToObjSeries(arguments?.getString("CHARACTER_SERIES_JSON")!!)
        } else {
            series = arguments?.get(CharacterListFragment.CHARACTER_SERIES)!!
        }

        _characterModelJson = arguments?.getString(CharacterListFragment.CHARACTER_MODEL_JSON)!!

        txtCharacterDetailsName.text = name
        txtDescription.text = description
        Picasso.get().load(thumbnail).into(image)

        if ((comics as List<ComicSummary>).size > 0) {
            for (comic in comics as List<ComicSummary>) {
                val chip = Chip(_view.context)
                chip.text = comic.name
                cgComics.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtComicsCharacters).visibility = View.GONE
        }

        if ((series as List<SeriesSummary>).size > 0) {
            for (serie in series as List<SeriesSummary>) {
                val chip = Chip(_view.context)
                chip.text = serie.name
                cgSeries.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtSeriesCharacters).visibility = View.GONE
        }

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
        currentUser?.uid?.let {
            _viewModelFavorite.checkIfIsFavorite(it, _idCharacter!!)
                .observe(viewLifecycleOwner, Observer { list ->

                    if (list.isEmpty()) {
                        color = R.color.color_white
                    } else {
                        isFavorite = true
                        color = R.color.color_red
                    }
                    charactersFavorites.setColorFilter(
                        ContextCompat.getColor(_view.context, color!!),
                        PorterDuff.Mode.SRC_IN
                    );
                })
        }

        setBackNavigation()
        setOnFavoriteClick()
    }

    fun jsonToObjComics(json: String): Any {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<List<ComicSummary>>() {}.type

        return gson.fromJson(json, arrayTutorialType)
    }

    fun jsonToObjSeries(json: String): Any {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<List<SeriesSummary>>() {}.type

        return gson.fromJson(json, arrayTutorialType)
    }

    private fun setOnFavoriteClick() {

        charactersFavorites.setOnClickListener {

            val currentUser = _auth.currentUser

            if (currentUser != null) {
                _user=currentUser.uid
                  favorite(_user!!)

            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivityForResult(intent, LoginFragment.REQUEST_CODE)
            }
        }
    }

    private fun favorite(user:String) {
        isFavorite = !isFavorite

        if (isFavorite) {
            color = R.color.color_red
            if (_idCharacter != null) {
                _viewModelFavorite.addFavorite(
                    user,
                    _idCharacter!!,
                    _characterModelJson,
                    1
                ).observe(viewLifecycleOwner, Observer {
                    Snackbar.make(_view, "Personagem favoritado", Snackbar.LENGTH_LONG)
                        .show()
                })
            }
        } else {
            _idCharacter?.let { it1 ->
                _viewModelFavorite.deleteFavorite(_user!!,it1)
                    .observe(viewLifecycleOwner, Observer {
                        Snackbar.make(
                            _view,
                            "Personagem removido dos favoritos",
                            Snackbar.LENGTH_LONG
                        ).show()
                    })
            }
            color = R.color.color_white
        }

        charactersFavorites.setColorFilter(
            ContextCompat.getColor(_view.context, color!!),
            PorterDuff.Mode.SRC_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(LoginFragment.REQUEST_CODE==requestCode && Activity.RESULT_OK==resultCode){
            if(_user  != null){
                favorite(_user!!)
            }
        }

    }
    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgCharactersDetailsBack).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}