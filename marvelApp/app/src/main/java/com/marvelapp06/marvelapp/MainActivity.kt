package com.marvelapp06.marvelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marvelapp06.marvelapp.character.view.CharacterFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.selectedItemId = R.id.home

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorite -> {
                    val intent = Intent(this, FavoritesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        val bundle = intent.extras
        if(bundle != null) {
            val frag = bundle.getString("KEY_FRAGMENT")
            if (frag.toString() == "CharacterFragment"){
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                val characterFragment = CharacterFragment()
                characterFragment.arguments = intent.extras
                transaction.add(R.id.nav_host_fragment_container, characterFragment)
                transaction.commit()
            }
        }

    }

    companion object {
        const val MARVEL_APP = "MarvelApp"
        const val KEEP_LOGGED = "KeepLogged"
    }
}
