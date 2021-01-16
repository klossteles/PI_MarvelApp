package com.marvelapp06.marvelapp.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.marvelapp06.marvelapp.R

class RecoverPasswordFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recover_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _auth = FirebaseAuth.getInstance()
        val navController = findNavController()

        val email = arguments?.getString(LoginFragment.EMAIL)
        if (email != null && email.isNotEmpty()) {
            _view.findViewById<TextInputEditText>(R.id.edtEmailRecover).setText(email)
        }

        onBack(navController)
        onRecoverPassword(navController)
    }

    private fun onRecoverPassword(navController: NavController) {
        _view.findViewById<Button>(R.id.btnRecover).setOnClickListener {
            val email = _view.findViewById<TextInputEditText>(R.id.edtEmailRecover)?.text.toString()
            if (email.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.txtEmailRecover).error =
                    getString(R.string.insert_email)
                return@setOnClickListener
            }
            _auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    Toast.makeText(
                        _view.context, getString(R.string.email_sent), Toast.LENGTH_SHORT
                    ).show()
                    navController.navigateUp()
                }
        }
    }

    private fun onBack(navController: NavController) {
        _view.findViewById<ImageView>(R.id.imgBackRecover).setOnClickListener {
            navController.navigateUp()
        }
    }
}