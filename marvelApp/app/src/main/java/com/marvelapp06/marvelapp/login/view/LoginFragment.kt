package com.marvelapp06.marvelapp.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.*
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity

class LoginFragment : Fragment() {
    private lateinit var _view:View
    private lateinit var _callbackManager: CallbackManager
    private lateinit var _googleSignInBtn: SignInButton
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var _loginMethod: String
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view
        val navController = findNavController()
        _callbackManager = CallbackManager.Factory.create()
        _view.findViewById<LoginButton>(R.id.containedButtonLoginFacebook).setOnClickListener { loginFacebook() }
        _googleSignInBtn = _view.findViewById<SignInButton>(R.id.containedButtonLoginGoogle)
        _googleSignInBtn.setSize(SignInButton.SIZE_STANDARD);
        _googleSignInBtn.setOnClickListener { googleSignIn() }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(_view.context, gso)

        _view.findViewById<Button>(R.id.containedButtonLogin).setOnClickListener {
            saveLoggedIn(true)
        }

        onRegister(navController)
        onRegisterSuccess(navController)
        onLogIn()
        onBack()
        onForgotPassword(navController)
    }

    private fun onBack() {
        _view.findViewById<ImageView>(R.id.imgBackLogin).setOnClickListener {
            activity?.finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val account = _auth.currentUser
        updateUI(account)
    }

    private fun googleSignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        _loginMethod = GOOGLE_LOGIN
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                activity?.finish()
            } else {
                Toast.makeText(_view.context, getString(R.string.validate_your_email), Toast.LENGTH_LONG).show()
                _auth.signOut()
                val intent = Intent(_view.context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun loginFacebook() {
        _loginMethod = FACEBOOK_LOGIN
        val instanceFirebase = LoginManager.getInstance()
        instanceFirebase.logInWithReadPermissions(this, listOf("email", "public_profile"))
        instanceFirebase.registerCallback(_callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val credential: AuthCredential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                _auth.signInWithCredential(credential).addOnCompleteListener { saveLoggedIn(true) }
            }

            override fun onCancel() {
                Toast.makeText(context, "Cancelado!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (_loginMethod) {
            FACEBOOK_LOGIN -> _callbackManager.onActivityResult(requestCode, resultCode, data)
            GOOGLE_LOGIN -> {
                // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
                if (requestCode == RC_SIGN_IN) {
                    // The Task returned from this call is always completed, no need to attach
                    // a listener.
                    val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val exception = task.exception
                    if (task.isSuccessful) {
                        try {
                            // Google Sign In was successful, authenticate with Firebase
                            val account = task.getResult(ApiException::class.java)!!
                            Log.d("LoginFragment", "firebaseAuthWithGoogle:" + account.id)
                            firebaseAuthWithGoogle(account.idToken!!)
                        } catch (e: ApiException) {
                            // Google Sign In failed, update UI appropriately
                            Log.w("MainActivity", "Google sign in failed", e)
                        }
                    } else {
                        Log.w("MainActivity", exception.toString())

                    }
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        _auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = _auth.currentUser
                    updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun onRegister(navController: NavController) {
        _view.findViewById<Button>(R.id.textButtonRegisterLogin).setOnClickListener {
            navController.navigate(R.id.action_loginFragment2_to_registerFragment3)
        }
    }

    private fun onRegisterSuccess(navController: NavController) {
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(RegisterFragment.EMAIL_REGISTER)
            ?.observe(viewLifecycleOwner) {
                _view.findViewById<TextInputEditText>(R.id.edtEmailLogin).setText(it)
            }
    }

    private fun saveLoggedIn(logged: Boolean = false) {
        val pref = _view.context.getSharedPreferences(
            MainActivity.MARVEL_APP,
            Context.MODE_PRIVATE
        )
        pref.edit().putBoolean(MainActivity.KEEP_LOGGED, logged).apply()
        if (logged) {
            val intent = Intent(_view.context, FavoritesActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun hideBackIcon() {
        _view.findViewById<ImageView>(R.id.imgBackLogin).visibility = View.GONE
    }

    private fun onLogIn() {
        _view.findViewById<Button>(R.id.containedButtonLogin).setOnClickListener {
            var success = true
            val email = _view.findViewById<TextInputEditText>(R.id.edtEmailLogin)?.text.toString()
            if (email.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.textEmailLogin).error =
                    getString(R.string.insert_email)
                success = false
            }
            val password =
                _view.findViewById<TextInputEditText>(R.id.edtPasswordLogin)?.text.toString()
            if (password.isEmpty()) {
                _view.findViewById<TextInputLayout>(R.id.textPasswordLogin).error =
                    getString(R.string.insert_password)
                success = false
            }
            if (success) {
                logIn(email, password)
            }
        }
    }

    private fun logIn(email: String, password: String) {
        _auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = _auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(_view.context, getString(R.string.an_error_has_occurred_check_email_and_password),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun onForgotPassword(navController: NavController) {
        _view.findViewById<MaterialButton>(R.id.btnForgotPasswordLogin).setOnClickListener {
            val email = _view.findViewById<TextInputEditText>(R.id.edtEmailLogin)?.text.toString()
            var bundle = bundleOf()
            if (email.isNotEmpty()) {
                bundle = bundleOf(EMAIL to email)
            }
            navController.navigate(R.id.action_loginFragment2_to_recoverPasswordFragment, bundle)
        }
    }

    companion object {
        const val RC_SIGN_IN = 9001
        const val FACEBOOK_LOGIN = "FACEBOOK"
        const val GOOGLE_LOGIN = "GOOGLE"
        const val EMAIL = "EMAIL"
    }
}