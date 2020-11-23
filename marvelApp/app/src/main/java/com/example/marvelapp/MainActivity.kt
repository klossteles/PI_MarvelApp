package com.example.marvelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<BottomNavigationView>(R.id.bottomNav).setOnNavigationItemReselectedListener {  item ->
            when(item.itemId) {
                R.id.home -> {
                    // Respond to navigation item 1 click
                    Toast.makeText(applicationContext, "HOME CLICKED", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.favorite -> {
                    Toast.makeText(applicationContext, "FAVORITE CLICKED", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.profile -> {
                    Toast.makeText(applicationContext, "PROFILE CLICKED", Toast.LENGTH_LONG).show()
                }
                else -> false
            }
        }
    }
}