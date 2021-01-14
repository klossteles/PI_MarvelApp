package com.marvelapp06.marvelapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.login.Login
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity
import com.marvelapp06.marvelapp.login.view.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marvelapp06.marvelapp.favorite.view.FavoriteFragment
import com.marvelapp06.marvelapp.login.view.LoginFragment

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val pref = this.getSharedPreferences(MainActivity.MARVEL_APP, Context.MODE_PRIVATE)
        val value = pref.getBoolean(MainActivity.KEEP_LOGGED, false)

        if (value) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.profile_nav_host_fragment, ProfileFragment())
                .commit()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(LoginFragment.HIDE_BACK, true)
            intent.putExtra(LoginFragment.CALLER, LoginFragment.PROFILE_CALLER)
            startActivity(intent)
            finish()
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
}