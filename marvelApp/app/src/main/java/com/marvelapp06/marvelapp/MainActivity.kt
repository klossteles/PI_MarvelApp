package com.marvelapp06.marvelapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marvelapp06.marvelapp.character.view.CharacterFragment
import com.marvelapp06.marvelapp.comic.view.ComicFragment
import com.marvelapp06.marvelapp.creator.view.CreatorsFragment
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity
import com.marvelapp06.marvelapp.series.view.SeriesFragment
import com.marvelapp06.marvelapp.utils.NetworkConnection
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
        val networkConnection = NetworkConnection(applicationContext)
        val bundle = intent.extras
        if (bundle != null) {
            when (bundle.getString("KEY_FRAGMENT")) {
                "CharacterFragment" -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    val characterFragment = CharacterFragment()

                    characterFragment.arguments = intent.extras
                    transaction.add(R.id.nav_host_fragment_container, characterFragment)
                    transaction.commit()
                    nav_host_fragment_container.visibility = View.VISIBLE
                    layoutDisconnected.visibility = View.GONE
                }
                "SeriesFragment" -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    val seriesFragment = SeriesFragment()

                    seriesFragment.arguments = intent.extras
                    transaction.add(R.id.nav_host_fragment_container, seriesFragment)
                    transaction.commit()
                    nav_host_fragment_container.visibility = View.VISIBLE
                    layoutDisconnected.visibility = View.GONE
                }
                "ComicFragment" -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    val comicFragment = ComicFragment()

                    comicFragment.arguments = intent.extras
                    transaction.add(R.id.nav_host_fragment_container, comicFragment)
                    transaction.commit()
                    nav_host_fragment_container.visibility = View.VISIBLE
                    layoutDisconnected.visibility = View.GONE
                }
                "CreatorsFragment" -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    val creatorsFragment = CreatorsFragment()

                    creatorsFragment.arguments = intent.extras
                    transaction.add(R.id.nav_host_fragment_container, creatorsFragment)
                    transaction.commit()
                    nav_host_fragment_container.visibility = View.VISIBLE
                    layoutDisconnected.visibility = View.GONE
                }
                else -> {
                    networkConnection.observe(this, Observer { isConnected ->
                        if (isConnected) {
                            nav_host_fragment_container.visibility = View.VISIBLE
                            layoutDisconnected.visibility = View.GONE
                        } else {
                            nav_host_fragment_container.visibility = View.GONE
                            layoutDisconnected.visibility = View.VISIBLE
                        }
                    })
                }
            }
        } else {
            networkConnection.observe(this, Observer { isConnected ->
                if (isConnected) {
                    nav_host_fragment_container.visibility = View.VISIBLE
                    layoutDisconnected.visibility = View.GONE
                } else {
                    nav_host_fragment_container.visibility = View.GONE
                    layoutDisconnected.visibility = View.VISIBLE
                }
            })
        }
    }
}
