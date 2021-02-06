package com.marvelapp06.marvelapp.creator.view

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.creator.repository.CreatorsRepository
import com.marvelapp06.marvelapp.creator.viewmodel.CreatorsViewModel
import com.marvelapp06.marvelapp.data.model.ComicSummary
import com.marvelapp06.marvelapp.data.model.EventSummary
import com.marvelapp06.marvelapp.data.model.SeriesSummary
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marvelapp06.marvelapp.LoginActivity
import com.marvelapp06.marvelapp.db.AppDatabase
import com.marvelapp06.marvelapp.favorite.repository.FavoriteRepository
import com.marvelapp06.marvelapp.favorite.viewmodel.FavoriteViewModel
import com.marvelapp06.marvelapp.login.view.LoginFragment
import com.squareup.picasso.Picasso

class CreatorsFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _viewModel: CreatorsViewModel
    private lateinit var _viewModelFavorite: FavoriteViewModel
    private lateinit var _auth: FirebaseAuth
    private var _idCreators: Int? = null
    private lateinit var _creatorsModelJson: String
    private var isFavorite: Boolean = false
    private var color: Int? = null
    private lateinit var creatorsFavorites: ImageView
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
        return inflater.inflate(R.layout.fragment_creators, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        creatorsFavorites = _view.findViewById(R.id.imgCreatorsDetailsFavorite)
        _viewModel = ViewModelProvider(
            this,
            CreatorsViewModel.CreatorsViewModelFactory(CreatorsRepository())
        ).get(CreatorsViewModel::class.java)

        val textCreatorsNameDetails = _view.findViewById<TextView>(R.id.txtFullnameCreatorsDetails)
        val image = _view.findViewById<ImageView>(R.id.imgCreatorDetails)
        val cgSeriesCreators = _view.findViewById<ChipGroup>(R.id.cgSeriesCreators)
        val cgComicsCreators = _view.findViewById<ChipGroup>(R.id.cgComicsCreators)
        val cgEventsCreators = _view.findViewById<ChipGroup>(R.id.cgEventsCreators)

        _idCreators=arguments?.getInt(CreatorsListFragment.CREATORS_ID)
        val thumbnail = arguments?.getString(CreatorsListFragment.CREATORS_THUMBNAIL)
        val fullname = arguments?.getString(CreatorsListFragment.CREATORS_FULL_NAME)
        var series:Any
        var comics:Any
        var events:Any

        if( arguments?.get(CreatorsListFragment.CREATORS_SERIES) == null) {
            series = jsonToObjSeries(arguments?.getString("CREATORS_SERIES_JSON")!!)
        } else {
            series = arguments?.get(CreatorsListFragment.CREATORS_SERIES)!!
        }

        if( arguments?.get(CreatorsListFragment.CREATORS_COMICS) == null) {
            comics = jsonToObjComics(arguments?.getString("CREATORS_COMICS_JSON")!!)
        } else {
            comics = arguments?.get(CreatorsListFragment.CREATORS_COMICS)!!
        }

        if(arguments?.get(CreatorsListFragment.CREATORS_EVENTS) == null) {
            events = jsonToObjEvents(arguments?.getString("CREATORS_EVENTS_JSON")!!)
        } else {
            events = arguments?.get(CreatorsListFragment.CREATORS_EVENTS)!!
        }

        _creatorsModelJson = arguments?.getString(CreatorsListFragment.CREATORS_MODEL_JSON)!!

        textCreatorsNameDetails.text = fullname

        Picasso.get().load(thumbnail).into(image)

        if ((series as List<SeriesSummary>).size >0){
            for (serie in series as List<SeriesSummary>){
                val chip = Chip(_view.context)
                if (serie.name != null) {
                    chip.text = "${serie.name}"
                } else {
                    chip.text = serie.name
                }
                cgSeriesCreators.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtSeriesCreatorsDetails).visibility = View.GONE
        }


        if ((comics as List<ComicSummary>).size >0){
            for (comic in comics as List<ComicSummary>){
                val chip = Chip(_view.context)
                if (comic.name != null) {
                    chip.text = "${comic.name}"
                } else {
                    chip.text = comic.name
                }
                cgComicsCreators.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtComicsCreatorsDetails).visibility = View.GONE
        }

        if ((events as List<EventSummary>).size >0){
            for (event in events as List<EventSummary>){
                val chip = Chip(_view.context)
                if (event.name != null) {
                    chip.text = "${event.name}"
                } else {
                    chip.text = event.name
                }
                cgEventsCreators.addView(chip)
            }
        } else {
            _view.findViewById<TextView>(R.id.txtEventsCreatorsDetails).visibility = View.GONE
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
            _viewModelFavorite.checkIfIsFavorite(it,_idCreators!!)
                .observe(viewLifecycleOwner, Observer { list ->

                    if (list.isEmpty()) {
                        color = R.color.color_white
                    } else {
                        isFavorite = true
                        color = R.color.color_red
                    }
                    creatorsFavorites.setColorFilter(
                        ContextCompat.getColor(_view.context, color!!),
                        PorterDuff.Mode.SRC_IN
                    )
                })
        }

        setBackNavigation()
        setOnFavoriteClick()
        shareCreator(fullname!!)
    }

    private fun jsonToObjSeries(json: String): Any {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<List<SeriesSummary>>() {}.type

        return gson.fromJson(json, arrayTutorialType)
    }

    private fun jsonToObjComics(json: String): Any {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<List<ComicSummary>>() {}.type

        return gson.fromJson(json, arrayTutorialType)
    }

    private fun jsonToObjEvents(json: String): Any {
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<List<EventSummary>>() {}.type

        return gson.fromJson(json, arrayTutorialType)
    }


    private fun setOnFavoriteClick() {

        creatorsFavorites.setOnClickListener {

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

    fun favorite(user:String){
        isFavorite = !isFavorite

        if (isFavorite) {
            color = R.color.color_red
            if (_idCreators != null) {
                _viewModelFavorite.addFavorite(
                    user,
                    _idCreators!!,
                    _creatorsModelJson,
                    4
                ).observe(viewLifecycleOwner, Observer {
                    Snackbar.make(_view, "Criador favoritado", Snackbar.LENGTH_LONG)
                        .show()
                })
            }
        } else {
            _idCreators?.let { it1 ->
                _viewModelFavorite.deleteFavorite( _user!!,it1)
                    .observe(viewLifecycleOwner, Observer {
                        Snackbar.make(
                            _view,
                            "Criador removido dos favoritos",
                            Snackbar.LENGTH_LONG
                        ).show()
                    })
            }
            color = R.color.color_white
        }

        creatorsFavorites.setColorFilter(
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
        _view.findViewById<ImageView>(R.id.imgCreatorsDetailsBack).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

    private fun shareCreator(name:String){
        val btnShare=_view.findViewById<ImageView>(R.id.imgCreatorsDetailsShare)
        btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Hey, here is a creator you might like:\n" +
                        "\n" +
                        "Name: $name\n" +
                        "\n" +
                        "To learn more about the works of this creator download Marvel App")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}