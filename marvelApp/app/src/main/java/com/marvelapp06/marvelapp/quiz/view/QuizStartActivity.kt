package com.marvelapp06.marvelapp.quiz.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.ProfileActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity
import kotlinx.android.synthetic.main.activity_quiz_start.*

class QuizStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_start)

        btnStartQuiz.setOnClickListener {
            val intent = Intent(this@QuizStartActivity, QuizQuestionActivity::class.java)
            startActivity(intent)
            finish()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.quizStartBottomNav)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
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
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        val imgBack = findViewById<ImageView>(R.id.imgBackQuiz)
        imgBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        }

    }
