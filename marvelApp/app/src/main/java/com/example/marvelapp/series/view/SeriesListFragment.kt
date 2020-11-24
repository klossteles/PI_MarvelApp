package com.example.marvelapp.series.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.series.model.SeriesModel
import com.example.marvelapp.series.repository.SeriesRepository
import com.example.marvelapp.series.viewmodel.SeriesViewModel
import java.util.*

class SeriesListFragment : Fragment() {
    private lateinit var _viewModel: SeriesViewModel
    private lateinit var _view:View
    private lateinit var _listAdapter: SeriesListAdapter
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _searchView: SearchView

    private var _series = mutableListOf<SeriesModel>()
    private var _title: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view

        val list = _view.findViewById<RecyclerView>(R.id.listSeries)
        val manager = GridLayoutManager(_view.context, 2)
        _series = mutableListOf<SeriesModel>()
        _listAdapter = SeriesListAdapter(_series) {
            val bundle = bundleOf(SERIES_ID to it.id)
            _view.findNavController().navigate(R.id.action_seriesListFragment_to_seriesFragment, bundle)
        }

        _recyclerView = _view.findViewById<RecyclerView>(R.id.listSeries)
        list.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _listAdapter
        }

        _viewModel = ViewModelProvider(
            this,
            SeriesViewModel.SeriesViewModelFactory(SeriesRepository())
        ).get(SeriesViewModel::class.java)

        _viewModel.getList().observe(viewLifecycleOwner, Observer {
            showResults(it)
        })
        showLoading(true)
        setScrollView()
        initSearch()
    }

    private fun initSearch() {
        _searchView = _view.findViewById<SearchView>(R.id.searchSeries)
        _searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                _searchView.clearFocus()
                _title = query
                if (query.isEmpty()) {
                    _viewModel.getList().observe(viewLifecycleOwner, Observer {
                        _series.clear()
                        showResults(it)
                    })
                } else {
                    _viewModel.search(query).observe(viewLifecycleOwner, Observer {
                        _series.clear()
                        showResults(it)
                    })
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isBlank()) {
                    _title = null
                    showResults(_viewModel.returnFirstList())
                }
                return true
            }
        })
        _viewModel.getList()
    }

    private fun showResults(list: List<SeriesModel>?) {
        showLoading(false)
        list?.isNotEmpty()?.let { notFound(it) }
        list?.let { _series.addAll(it) }
        _listAdapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.seriesLoading)

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
                        _viewModel.nextPage(_title).observe(viewLifecycleOwner, Observer {
                            showResults(it)
                        })
                    }
                }
            })
        }
    }

    companion object {
        const val SERIES_ID = "SERIES_ID"
    }
}