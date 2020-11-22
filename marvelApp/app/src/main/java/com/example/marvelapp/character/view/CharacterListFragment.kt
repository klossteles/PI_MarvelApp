package com.example.marvelapp.character.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.character.model.CharactersModel
import com.example.marvelapp.character.repository.CharacterRepository
import com.example.marvelapp.character.viewmodel.CharacterViewModel


class CharacterListFragment : Fragment() {
    private lateinit var _viewModel: CharacterViewModel
    private lateinit var _view: View
    private lateinit var _listAdapter: CharacterListAdapter
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _searchView: SearchView

    private var _characters = mutableListOf<CharactersModel>()
    private var _nameCharacter: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view = inflater.inflate(R.layout.fragment_character_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view

        val list = _view.findViewById<RecyclerView>(R.id.listCharacters)
        val manager = GridLayoutManager(_view.context, 2)
        _characters = mutableListOf<CharactersModel>()
        _listAdapter = CharacterListAdapter(_characters) {
            val bundle = bundleOf(CHARACTER_ID to it.id)
            _view.findNavController()
                .navigate(R.id.action_characterListFragment_to_characterActivity, bundle)
        }

        list.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _listAdapter
        }

        _viewModel = ViewModelProvider(
            this,
            CharacterViewModel.CharacterViewModelFactory(CharacterRepository())
        ).get(CharacterViewModel::class.java)

        _viewModel.getListCharacters().observe(viewLifecycleOwner, Observer {
            showResults(it)
        })

        showLoading(true)
        setScrollView()
        initSearch()
    }

    private fun showResults(list: List<CharactersModel>?) {
        showLoading(false)
        list?.isNotEmpty()?.let { notFound(it) }
        list?.let { _characters.addAll(it) }
        _listAdapter.notifyDataSetChanged()
    }

    private fun initSearch() {
        _searchView = _view.findViewById<SearchView>(R.id.searchCharacters)
        _searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                _searchView.clearFocus()
                _nameCharacter = query

                if (query!!.isEmpty()) {
                    _viewModel.getListCharacters().observe(viewLifecycleOwner, Observer {
                        _characters.clear()
                        showResults(it)
                    })
                } else {
                    _viewModel.searchCharacter(query).observe(viewLifecycleOwner, Observer {
                        _characters.clear()
                        showResults(it)
                    })
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isBlank()) {
                    _nameCharacter = null
                    showResults(_viewModel.returnFirstListCharacters())
                }
                return true
            }

        })
        _viewModel.getListCharacters()
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.characterLoading)

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
                        _viewModel.nextPage().observe(viewLifecycleOwner, Observer {
                            showResults(it)
                        })
                    }
                }
            })
        }
    }

    companion object {
        const val CHARACTER_ID = "CHARACTER_ID"
    }
}