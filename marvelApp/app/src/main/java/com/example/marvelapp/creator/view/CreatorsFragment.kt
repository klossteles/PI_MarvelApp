package com.example.marvelapp.creator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.marvelapp.R
import com.example.marvelapp.creator.model.CreatorsModel
import com.example.marvelapp.creator.repository.CreatorsRepository
import com.example.marvelapp.creator.viewmodel.CreatorsViewModel
import com.example.marvelapp.series.view.*
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class CreatorsFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _viewModel: CreatorsViewModel

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
        _viewModel = ViewModelProvider(
            this,
            CreatorsViewModel.CreatorsViewModelFactory(CreatorsRepository())
        ).get(CreatorsViewModel::class.java)


        val pager = _view.findViewById<ViewPager>(R.id.creatorsViewPager)
        val tab = _view.findViewById<TabLayout>(R.id.creatorsTabLayout)

        tab.setupWithViewPager(pager)

        val fragments = mutableListOf<Fragment>()
        val titles = listOf(getString(R.string.comics), getString(R.string.series),
            getString(R.string.events))

        showLoading(true)

        val textCreatorsNameDetails = _view.findViewById<TextView>(R.id.textCreatorsNameDetails)
        val image = _view.findViewById<ImageView>(R.id.imgCreatorDetails)
        val creatorsId = arguments?.getInt(CreatorsListFragment.CREATORS_ID)


        if (creatorsId != null) {
            _viewModel.getCreatorsById(creatorsId).observe(viewLifecycleOwner, Observer {
                showLoading(false)
                textCreatorsNameDetails.text = it.fullName
                Picasso.get().load(it.thumbnail?.getImagePath()).into(image)
                fragments.add(CreatorsComicsListFragment.newInstance(it.comics))
                fragments.add(CreatorsSeriesListFragment.newInstance(it.series))
                fragments.add(CreatorsEventsListFragment.newInstance(it.events))
                pager.adapter = activity?.supportFragmentManager?.let { it1 ->
                    ViewPageAdapter(fragments, titles,
                        it1
                    )
                }
            })
        }
        showLoading(false)
        setBackNavigation()
    }

    private fun setBackNavigation() {
        _view.findViewById<ImageView>(R.id.imgCreatorsDetailsBack).setOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.creatorsDetailsLoading)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }
}