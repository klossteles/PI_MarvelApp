package com.example.marvelapp.comic.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.comic.model.ComicsModel
import com.example.marvelapp.comic.viewModel.ComicViewModel



class SearchComicFragment : Fragment() {
    private lateinit var _viewModel: ComicViewModel
    private lateinit var _view: View
    private lateinit var _listAdapter: ComicListAdapter
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _searchView: SearchView

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

        val list = _view.findViewById<RecyclerView>(R.id.listComic)
        val manager = GridLayoutManager(_view.context, 2)
        _comic = mutableListOf<ComicsModel>()
        _listAdapter = ComicListAdapter(_comic) {
            val bundle = bundleOf(SearchComicFragment.COMIC_ID to it.id)
            _view.findNavController()
                .navigate(R.id.action_searchComicFragment_to_comicFragment, bundle)
        }
        _recyclerView = _view.findViewById<RecyclerView>(R.id.listComic)
        list.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _listAdapter
        }

    }
    companion object {
        const val COMIC_ID = "COMIC_ID"
    }
}
