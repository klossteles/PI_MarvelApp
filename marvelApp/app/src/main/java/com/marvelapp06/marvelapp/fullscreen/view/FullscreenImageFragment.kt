package com.marvelapp06.marvelapp.fullscreen.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.marvelapp06.marvelapp.R
import com.squareup.picasso.Picasso

class FullscreenImageFragment : DialogFragment() {
    private lateinit var _view: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fullscreen_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        val image = _view.findViewById<ImageView>(R.id.imgFullscreen)
        val coverThumbnail = arguments?.getString(THUMBNAIL)
        Picasso.get().load(coverThumbnail).into(image)
        onCloseDialog()
    }

    private fun onCloseDialog() {
        _view.findViewById<ImageView>(R.id.icCloseDialog).setOnClickListener {
            dismiss()
            findNavController().navigateUp()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    companion object {
        const val THUMBNAIL = "THUMBNAIL"
    }
}