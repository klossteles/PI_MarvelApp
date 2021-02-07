package com.marvelapp06.marvelapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity
import com.marvelapp06.marvelapp.login.view.LoginActivity
import com.marvelapp06.marvelapp.login.view.LoginFragment
import com.marvelapp06.marvelapp.login.view.ProfileFragment
import com.marvelapp06.marvelapp.utils.NetworkConnection
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setFields()
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                profile_nav_host_fragment.visibility = View.VISIBLE
                layoutDisconnectedProfile.visibility = View.GONE
            } else {
                profile_nav_host_fragment.visibility = View.GONE
                layoutDisconnectedProfile.visibility = View.VISIBLE
            }
        })
    }

    private fun setFields() {
        _auth = FirebaseAuth.getInstance()
        val currentUser = _auth.currentUser

        if (currentUser != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.profile_nav_host_fragment, ProfileFragment())
                .commit()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, LoginFragment.REQUEST_CODE)
        }

        val bottomNavigationView =  findViewById<BottomNavigationView>(R.id.profileBottomNav)
        bottomNavigationView.selectedItemId = R.id.profile

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.favorite -> {
                    val intent = Intent(this, FavoritesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginFragment.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val loggedIn = data!!.getBooleanExtra(LoginFragment.LOGGED_IN, false)
                if (loggedIn) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.profile_nav_host_fragment, ProfileFragment())
                        .commit()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.its_necessary_to_logged_to_access_profile),
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.its_necessary_to_logged_to_access_profile),
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}