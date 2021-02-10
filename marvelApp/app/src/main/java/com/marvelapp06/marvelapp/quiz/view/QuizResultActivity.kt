package com.marvelapp06.marvelapp.quiz.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marvelapp06.marvelapp.MainActivity
import com.marvelapp06.marvelapp.ProfileActivity
import com.marvelapp06.marvelapp.R
import com.marvelapp06.marvelapp.favorite.view.FavoritesActivity
import com.marvelapp06.marvelapp.quiz.model.Constants
import kotlinx.android.synthetic.main.activity_quiz_result.*

class QuizResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        tv_score.text = getString(R.string.your_score_is_out_of, correctAnswers, totalQuestions)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.quizResultBottomNav)

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
    }
}