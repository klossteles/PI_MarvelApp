package com.marvelapp06.marvelapp.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marvelapp06.marvelapp.R
import kotlinx.android.synthetic.main.activity_quiz_start.*

class QuizStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_start)

        btnStartQuiz.setOnClickListener{
            val intent = Intent(this@QuizStartActivity, QuizQuestionActivity::class.java)
            startActivity(intent)
            finish()

        }


    }
}