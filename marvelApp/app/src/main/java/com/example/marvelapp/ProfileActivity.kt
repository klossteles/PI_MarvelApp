package com.example.marvelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.marvelapp.login.view.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportFragmentManager.beginTransaction()
            .replace(R.id.profileFrameLayout, ProfileFragment())
            .commit()

        findViewById<BottomNavigationView>(R.id.profileBottomNav).setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.favorite -> {
                    Toast.makeText(applicationContext, "FAVORITE CLICKED", Toast.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }
    }
}