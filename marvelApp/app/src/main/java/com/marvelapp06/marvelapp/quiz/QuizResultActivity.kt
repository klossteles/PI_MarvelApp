package com.marvelapp06.marvelapp.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marvelapp06.marvelapp.R
import kotlinx.android.synthetic.main.activity_quiz_result.*

class QuizResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        tv_score.text = getString(R.string.your_score_is_out_of, correctAnswers, totalQuestions)
        }
    }