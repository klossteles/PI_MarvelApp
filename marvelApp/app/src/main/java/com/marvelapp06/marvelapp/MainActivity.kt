package com.marvelapp06.marvelapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marvelapp06.marvelapp.character.view.CharacterFragment
import com.marvelapp06.marvelapp.comic.view.ComicFragment
import com.marvelapp06.marvelapp.creator.view.CreatorsFragment
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity
import com.marvelapp06.marvelapp.series.view.SeriesFragment
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import kotlinx.android.synthetic.main.activity_main.*

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
        if (bundle != null) {
            when (bundle.getString("KEY_FRAGMENT")) {
                CHARACTER_FRAGMENT -> {
                    val characterFragment = CharacterFragment()
                    addTransaction(characterFragment)
                }
                SERIES_FRAGMENT -> {
                    val seriesFragment = SeriesFragment()
                    addTransaction(seriesFragment)
                }
                COMIC_FRAGMENT -> {
                    val comicFragment = ComicFragment()
                    addTransaction(comicFragment)
                }
                CREATORS_FRAGMENT -> {
                    val creatorsFragment = CreatorsFragment()
                    addTransaction(creatorsFragment)
                }
            }

        }
    }

    private fun addTransaction(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        fragment.arguments = intent.extras
        transaction.add(R.id.nav_host_fragment_container, fragment)
        transaction.commit()
    }

    companion object {
        const val CHARACTER_FRAGMENT = "CharacterFragment"
        const val SERIES_FRAGMENT = "SeriesFragment"
        const val COMIC_FRAGMENT = "ComicFragment"
        const val CREATORS_FRAGMENT = "CreatorsFragment"
    }
}
