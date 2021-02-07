package com.marvelapp06.marvelapp.login.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.R

class ProfileFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var _auth: FirebaseAuth
    private lateinit var _initialName: String
    private lateinit var _initialEmail: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        _auth = FirebaseAuth.getInstance()
        onSignOut()
        val user = _auth.currentUser
        if (user != null) {
            _initialEmail = user.email.toString()
            _initialName = user.displayName.toString()
            setUser(user)
            onUpdate(user)
        }

        checkNameChanged()
        checkEmailChanged()
        checkPasswordChanged()
    }

    private fun checkEmailChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtEmailProfile)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<Button>(R.id.containedButtonSaveProfile).visibility = View.VISIBLE
                    _view.findViewById<TextInputLayout>(R.id.textEmailProfile).error = ""
                }
            })
    }

    private fun checkNameChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtFullNameProfile)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<Button>(R.id.containedButtonSaveProfile).visibility = View.VISIBLE
                    _view.findViewById<TextInputLayout>(R.id.textFullnameProfile).error = ""
                }
            })
    }



    private fun checkPasswordChanged() {
        _view.findViewById<TextInputEditText>(R.id.edtPasswordProfile)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    _view.findViewById<Button>(R.id.containedButtonSaveProfile).visibility = View.VISIBLE
                    _view.findViewById<TextInputLayout>(R.id.textPasswordProfile).error = ""
                }
            })
    }

    private fun setUser(user: FirebaseUser?) {
        if (user != null) {
            val email = user.email
            val fullName = user.displayName

            if (user.providerData.get(1).providerId == EmailAuthProvider.PROVIDER_ID) {
                if (email != null) {
                    _view.findViewById<TextInputEditText>(R.id.edtEmailProfile).setText(email)
                    _view.findViewById<TextInputLayout>(R.id.textEmailProfile).visibility = View.VISIBLE
                    _view.findViewById<TextInputLayout>(R.id.textPasswordProfile).visibility = View.VISIBLE
                }
            } else {
                _view.findViewById<TextInputLayout>(R.id.textEmailProfile).visibility = View.GONE
                _view.findViewById<TextInputLayout>(R.id.textPasswordProfile).visibility = View.GONE
            }

            if (fullName != null) {
                _view.findViewById<TextInputEditText>(R.id.edtFullNameProfile).setText(fullName)
                _view.findViewById<TextView>(R.id.textViewHelloName).text = getString(R.string.hello_name, fullName)
            }
        }
    }

    private fun onUpdate(user: FirebaseUser) {
        _view.findViewById<Button>(R.id.containedButtonSaveProfile).setOnClickListener {
            val fullName = _view.findViewById<TextInputEditText>(R.id.edtFullNameProfile)?.text.toString()
            if (fullName.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.textFullnameProfile).error = getString(R.string.insert_name)
                return@setOnClickListener
            }
            val email = _view.findViewById<TextInputEditText>(R.id.edtEmailProfile)?.text.toString()
            if (email.isEmpty()){
                _view.findViewById<TextInputLayout>(R.id.textEmailProfile).error = getString(R.string.insert_email)
                return@setOnClickListener
            }
            val password = _view.findViewById<TextInputEditText>(R.id.edtPasswordProfile)?.text.toString()
            if (password.isNotEmpty() && password.length < 6) {
                _view.findViewById<TextInputLayout>(R.id.textPasswordProfile).error = getString(R.string.must_contain_at_least_6_characters)
                return@setOnClickListener
            }

            if (_view.findViewById<Button>(R.id.containedButtonSaveProfile).visibility == View.VISIBLE) {
                addConfirmDialog(user, fullName, email, password)
            }
        }
    }


    private fun onSignOut() {
        _view.findViewById<ImageView>(R.id.imgLogout).setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        _auth.signOut()
        val intent = Intent(_view.context, LoginActivity::class.java)
        startActivityForResult(intent, LoginFragment.REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginFragment.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val loggedIn = data!!.getBooleanExtra(LoginFragment.LOGGED_IN, false)
                if (loggedIn) {
                    val user = _auth.currentUser
                    setUser(user)
                } else {
                    Toast.makeText(_view.context,
                        getString(R.string.its_necessary_to_logged_to_access_profile),
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(_view.context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            } else {
                Toast.makeText(_view.context,
                    getString(R.string.its_necessary_to_logged_to_access_profile),
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(_view.context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun addConfirmDialog(
        user: FirebaseUser,
        fullName: String,
        email: String,
        password: String
    ) {
        val toast: Toast? = null
        context?.let { it1 ->
            toast?.cancel()
            val confirmView = layoutInflater.inflate(R.layout.confirm_dialog, null)
            addDialog(it1, confirmView, user, fullName, email, password)
            checkConfirmFields(confirmView)
        }
    }

    private fun checkConfirmFields(confirmView: View) {
        confirmView.findViewById<TextInputEditText>(R.id.edtEmailConfirm)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    confirmView.findViewById<TextInputLayout>(R.id.txtEmailConfirm).error = ""
                }
            })

        confirmView.findViewById<TextInputEditText>(R.id.edtPasswordConfirm)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    confirmView.findViewById<TextInputLayout>(R.id.txtPasswordConfirm).error = ""
                }
            })
    }

    private fun addDialog(
        it1: Context,
        confirmDialogView: View,
        user: FirebaseUser,
        fullName: String,
        email: String,
        password: String
    ) {

        val dialog = MaterialAlertDialogBuilder(it1)
            .setView(confirmDialogView)
            .show()

        val currentEmail = user.email
        confirmDialogView.findViewById<TextInputEditText>(R.id.edtEmailConfirm).setText(currentEmail)

        confirmDialogView.findViewById<Button>(R.id.btnCancelConfirmDialog).setOnClickListener {
            Toast.makeText(context, getString(R.string.update_canceled), Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        confirmDialogView.findViewById<Button>(R.id.btnConfirmConfirmDialog).setOnClickListener {
            val txtEmailConfirm = confirmDialogView.findViewById<TextInputLayout>(R.id.txtEmailConfirm)
            val emailConfirm = txtEmailConfirm.editText?.text.toString()

            if (emailConfirm.isEmpty()) {
                txtEmailConfirm.error = getString(R.string.insert_email)
                return@setOnClickListener
            }

            val txtPasswordConfirm = confirmDialogView.findViewById<TextInputLayout>(R.id.txtPasswordConfirm)
            val passwordConfirm = txtPasswordConfirm.editText?.text.toString()

            if (passwordConfirm.isEmpty()) {
                txtPasswordConfirm.error = getString(R.string.insert_password)
                return@setOnClickListener
            }
            _auth.signInWithEmailAndPassword(emailConfirm, passwordConfirm)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var reloadUserInfo = false
                        if (fullName != _initialName) {
                            reloadUserInfo = true
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(
                                    fullName
                                )
                                .build()
                            user.updateProfile(profileUpdates)
                        }

                        if (email.isNotEmpty() && email != _initialEmail) {
                            user.updateEmail(email).addOnSuccessListener {
                                reloadUserInfo = true
                            }
                        }
                        if (password.isNotEmpty()) {
                            user.updatePassword(password).addOnCompleteListener {
                                signOut()
                            }
                        }
                        dialog.dismiss()
                        _view.findViewById<Button>(R.id.containedButtonSaveProfile).visibility = View.GONE
                        if (reloadUserInfo) {
                            reloadUser()
                        }
                    } else {
                        Toast.makeText(_view.context, getString(R.string.invalid_email_or_password), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun reloadUser () {
        var user = _auth.currentUser
        user?.reload()?.addOnCompleteListener {
            user = _auth.currentUser
            setUser(user)
        }
    }
}