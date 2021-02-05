package com.marvelapp06.marvelapp.comic.view

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
import com.marvelapp06.marvelapp.comic.model.ComicsModel
import com.marvelapp06.marvelapp.comic.repository.ComicRepository
import com.marvelapp06.marvelapp.comic.viewModel.ComicViewModel
import com.marvelapp06.marvelapp.utils.NetworkConnection

class SearchComicFragment : Fragment() {
    private lateinit var _viewModel: ComicViewModel
    private lateinit var _view: View
    private lateinit var _listAdapter: ComicListAdapter
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _searchView: SearchView
    private var _hasConnection: Boolean = false

    private var _comic = mutableListOf<ComicsModel>()
    private var _title: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_comic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view

        //val list = _view.findViewById<RecyclerView>(R.id.listComic)
        val manager = GridLayoutManager(_view.context, 2)
        _comic = mutableListOf()
        _listAdapter = ComicListAdapter(_comic) {
            val bundle = bundleOf(
                COMIC_ID to it.id,
                COMIC_DESCRIPTION to it.description,
                COMIC_PRICE to it.prices,
                COMIC_CHARACTERS to it.characters?.items,
                COMIC_CREATORS to it.creators?.items,
                COMIC_IMAGES to it.images,
                COMIC_DATES to it.dates,
                COMIC_PAGES to it.pageCount,
                COMIC_THUMBNAIL to it.thumbnail?.getImagePath("landscape_incredible"),
                COMIC_TITLE to it.title,
                COMIC_MODEL_JSON to this.objToJson(it)
            )
            _view.findNavController()
                .navigate(R.id.action_searchComicFragment_to_comicFragment, bundle)
        }

        _recyclerView = _view.findViewById<RecyclerView>(R.id.listComic)
        _recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _listAdapter
        }

        _viewModel = ViewModelProvider(
            this,
            ComicViewModel.ComicViewModelFactory(ComicRepository())
        ).get(ComicViewModel::class.java)

        _viewModel.getList().observe(viewLifecycleOwner, Observer {
            showResults(it)
        })
        showLoading(true)
        setScrollView()
        initSearch()

        val networkConnection = NetworkConnection(_view.context)
        networkConnection.observe(viewLifecycleOwner, Observer { isConnected ->
            _hasConnection = isConnected
            if (isConnected) {
                _view.findViewById<LinearLayout>(R.id.internetNotFoundComicsList).visibility = View.GONE
                _recyclerView.visibility = View.VISIBLE
            } else {
                _view.findViewById<LinearLayout>(R.id.internetNotFoundComicsList).visibility = View.VISIBLE
                _recyclerView.visibility = View.GONE
            }
        })
    }

    private fun objToJson(comicModel: ComicsModel):String{
        val gson = Gson()
        return gson.toJson(comicModel)
    }

    private fun initSearch() {
        _searchView = _view.findViewById<SearchView>(R.id.searchComic)
        _searchView.setQuery("", false)
        _title = null
        _searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                _searchView.clearFocus()
                _title = query

                if (_hasConnection) {
                    if (query.isEmpty()) {
                        _viewModel.getList().observe(viewLifecycleOwner, Observer {
                            _comic.clear()
                            showLoading(true)
                            showResults(it)
                        })
                    } else {
                        _viewModel.search(query).observe(viewLifecycleOwner, Observer {
                            _comic.clear()
                            showLoading(true)
                            showResults(it)
                        })
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isBlank() && _hasConnection) {
                    _title = null
                    _comic.clear()
                    showLoading(true)
                    showResults(_viewModel.returnFirstList())
                }
                return true
            }
        })
        _viewModel.getList()
    }

    private fun showResults(list: List<ComicsModel>) {
        showLoading(false)
        notFound(list.isEmpty())
        list.let { _comic.addAll(it) }
        _listAdapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.comicLoading)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }

    private fun notFound(notFound: Boolean) {
        if (notFound) {
            _view.findViewById<View>(R.id.notFoundLayoutComic).visibility = View.VISIBLE
        } else {
            _view.findViewById<View>(R.id.notFoundLayoutComic).visibility = View.GONE
        }

        setBackNavigation()
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
                        _viewModel.nextPage(_title).observe(viewLifecycleOwner, Observer {
                            showResults(it)
                        })
                    }
                }
            })
        }
    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgBackComic).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()
        initSearch()
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
        const val COMIC_CHARACTERS = "COMIC_CHARACTERS"
        const val COMIC_CREATORS = "COMIC_CREATORS"
        const val COMIC_MODEL_JSON="COMIC_MODEL_JSON"
    }
}