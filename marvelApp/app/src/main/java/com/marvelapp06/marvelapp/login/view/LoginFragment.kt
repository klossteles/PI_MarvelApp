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
import androidx.fragment.app.Fragment
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
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity


class LoginFragment : Fragment() {
    private lateinit var _view:View
    private lateinit var _callbackManager: CallbackManager
    private lateinit var _googleSignInBtn: SignInButton
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var _loginMethod: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _view = inflater.inflate(R.layout.fragment_login, container, false)
        _callbackManager = CallbackManager.Factory.create()
        _view.findViewById<LoginButton>(R.id.containedButtonLoginFacebook).setOnClickListener { loginFacebook() }
        _googleSignInBtn = _view.findViewById<SignInButton>(R.id.containedButtonLoginGoogle)
        _googleSignInBtn.setSize(SignInButton.SIZE_STANDARD);
        _googleSignInBtn.setOnClickListener { googleSignIn() }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(_view.context, gso)

        // Inflate the layout for this fragment
        return _view
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(_view.context)
        updateUI(account)
    }

    private fun googleSignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        _loginMethod = GOOGLE_LOGIN
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            saveLoggedIn(true)
        }
    }

    private fun loginFacebook() {
        _loginMethod = FACEBOOK_LOGIN
        val instanceFirebase = LoginManager.getInstance()
        instanceFirebase.logInWithReadPermissions(this, listOf("email", "public_profile"))
        instanceFirebase.registerCallback(_callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val credential: AuthCredential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { saveLoggedIn(true) }
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
                    handleSignInResult(task)
                }
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG,
                "signInResult:failed code=" + e.message
            )
            updateUI(null)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _view = view

        val hide = arguments?.getBoolean(HIDE_BACK)
        if (hide != null && hide) {
            hideBackIcon()
        }

        _view.findViewById<Button>(R.id.containedButtonLogin).setOnClickListener {
            saveLoggedIn(true)
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

    companion object {
        const val HIDE_BACK = "HIDE_BACK"
        const val RC_SIGN_IN = 9001
        const val TAG = "GoogleActivity"
        const val FACEBOOK_LOGIN = "FACEBOOK"
        const val GOOGLE_LOGIN = "GOOGLE"
    }
}