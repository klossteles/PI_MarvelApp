package com.example.marvelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.marvelapp.creator.view.CreatorsListAdapter
import com.example.marvelapp.home.view.HomeFragment
import com.example.marvelapp.login.view.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var _view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bottomNav).setOnNavigationItemReselectedListener {  item ->
            when(item.itemId) {
                R.id.home -> {
                    val homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_container, homeFragment)
                        .commit()

                    // Respond to navigation item 1 click
                    Toast.makeText(applicationContext, "HOME CLICKED", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.favorite -> {
                    Toast.makeText(applicationContext, "FAVORITE CLICKED", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.profile -> {

                    val profileFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_container, profileFragment)
                        .commit()

                }
                else -> false
            }
        }
    }
}