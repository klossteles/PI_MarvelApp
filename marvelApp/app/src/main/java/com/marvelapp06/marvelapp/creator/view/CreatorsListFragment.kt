package com.marvelapp06.marvelapp.creator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.creator.model.CreatorsModel
import com.marvelapp06.marvelapp.creator.repository.CreatorsRepository
import com.marvelapp06.marvelapp.creator.viewmodel.CreatorsViewModel
import com.marvelapp06.marvelapp.utils.NetworkConnection

class CreatorsListFragment : Fragment() {

    private lateinit var _viewModel: CreatorsViewModel
    private lateinit var _view: View
    private lateinit var _listAdapter: CreatorsListAdapter
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _searchView: SearchView
    private var _hasConnection: Boolean = false

    private var _creators = mutableListOf<CreatorsModel>()
    private var _name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creators_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        val list = _view.findViewById<RecyclerView>(R.id.listCreators)
        val manager = GridLayoutManager(_view.context, 2)
        _creators = mutableListOf<CreatorsModel>()
        _listAdapter = CreatorsListAdapter(_creators) {
            val bundle = bundleOf(
                CREATORS_ID to it.id,
                CREATORS_THUMBNAIL to it.thumbnail?.getImagePath("landscape_incredible"),
                CREATORS_FULLNAME to it.fullName,
                CREATORS_SERIES to it.series?.items,
                CREATORS_COMICS to it.comics?.items,
                CREATORS_EVENTS to it.events?.items,
                CREATORS_MODEL_JSON to this.objToJson(it)
            )
            _view.findNavController()
                .navigate(R.id.action_creatorsListFragment_to_creatorsFragment, bundle)
        }

        _recyclerView = _view.findViewById<RecyclerView>(R.id.listCreators)
        list.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _listAdapter
        }

        _viewModel = ViewModelProvider(
            this,
            CreatorsViewModel.CreatorsViewModelFactory(CreatorsRepository())
        ).get(CreatorsViewModel::class.java)

        _viewModel.getListCreators().observe(viewLifecycleOwner, Observer {
            _creators.clear()
            showResults(it)
        })
        showLoading(true)
        setScrollView()
        initSearch()
        setBackNavigation()

        val networkConnection = NetworkConnection(_view.context)
        networkConnection.observe(viewLifecycleOwner, Observer { isConnected ->
            _hasConnection = isConnected
            if (isConnected) {
                _view.findViewById<LinearLayout>(R.id.internetNotFoundCreatorsList).visibility = View.GONE
                _recyclerView.visibility = View.VISIBLE
            } else {
                _view.findViewById<LinearLayout>(R.id.internetNotFoundCreatorsList).visibility = View.VISIBLE
                _recyclerView.visibility = View.GONE
            }
        })
    }

    private fun objToJson(creatorsModel: CreatorsModel):String{
        val gson = Gson()
        return gson.toJson(creatorsModel)
    }

    private fun initSearch() {
        _searchView = _view.findViewById<SearchView>(R.id.searchCreators)
        _searchView.setQuery("", false)
        _name = null
        _searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                _searchView.clearFocus()
                _name = query
                if (_hasConnection) {
                    if (query.isEmpty()) {
                        _viewModel.getListCreators().observe(viewLifecycleOwner, Observer {
                            _creators.clear()
                            showResults(it)
                        })
                    } else {
                        _viewModel.searchCreator(query).observe(viewLifecycleOwner, Observer {
                            _creators.clear()
                            showResults(it)
                        })
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isBlank() && _hasConnection) {
                    _name = null
                    _creators.clear()
                    showResults(_viewModel.returnFirstListCreators())
                }
                return true
            }
        })
        _viewModel.getListCreators()
    }

    private fun showResults(list: List<CreatorsModel>) {
        showLoading(false)
        list?.isNotEmpty()?.let { notFound(it) }
        list?.let { _creators.addAll(it) }
        _listAdapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.creatorsLoading)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }

    private fun notFound(notFound: Boolean) {
        if (notFound) {
            _view.findViewById<View>(R.id.notFoundLayout).visibility = View.GONE
        } else {
            _view.findViewById<View>(R.id.notFoundLayout).visibility = View.VISIBLE
        }
    }

    private fun setScrollView() {
        _recyclerView.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val target = recyclerView.layoutManager as LinearLayoutManager?
                    val totalItemCount = target!!.itemCount
                    val lastVisible = target.findLastVisibleItemPosition()
                    val lastItem = lastVisible + 4 >= totalItemCount
                    if (totalItemCount > 0 && lastItem) {
                        _viewModel.nextPage(_name).observe(viewLifecycleOwner, Observer {
                            showResults(it)
                        })
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        showLoading(true)
        initSearch()
    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgBackCreatorsList).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

    companion object {

        const val CREATORS_ID = "CREATORS_ID"
        const val CREATORS_THUMBNAIL = "CREATORS_THUMBNAIL"
        const val CREATORS_FULLNAME = "CREATORS_FULLNAME"
        const val CREATORS_SERIES = "CREATORS_SERIES"
        const val CREATORS_COMICS = "CREATORS_COMICS"
        const val CREATORS_EVENTS = "CREATORS_EVENTS"
        const val CREATORS_MODEL_JSON="CREATORS_MODEL_JSON"

    }
}