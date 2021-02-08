package com.marvelapp06.marvelapp.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.marvelapp06.marvelapp.R
import kotlinx.android.synthetic.main.activity_quiz_question.*

//onBackPressed()

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mQuestionsList = Constants.getQuestions()

        setQuestion()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        tv_option_five.setOnClickListener(this)
        buttonSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(tv_option_one, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(tv_option_two, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(tv_option_three, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(tv_option_four, 4)
            }
            R.id.tv_option_five -> {
                selectedOptionView(tv_option_five, 5)
            }
            R.id.buttonSubmit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent =
                                Intent(this@QuizQuestionActivity, QuizResultActivity::class.java)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        buttonSubmit.text = getString(R.string.finish)
                    } else {
                        buttonSubmit.text = getString(R.string.next_question)
                    }

                    mSelectedOptionPosition = 0
                }

            }
        }
    }


@SuppressLint("SetTextI18n")
    private fun setQuestion() {
        val question = mQuestionsList!!.get(mCurrentPosition - 1)

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            buttonSubmit.text = getString(R.string.finish)
        } else {
            buttonSubmit.text = getString(R.string.submit)
        }

        progressBarQuestions.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBarQuestions.max

        tv_question.text = question.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
        tv_option_five.text = question.optionFive
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background =
            ContextCompat.getDrawable(this@QuizQuestionActivity, R.drawable.selected_option_border)
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)
        options.add(4, tv_option_five)

        for (option in options) {
            option.setTextColor(ContextCompat.getColor(this, R.color.color_black))
            option.typeface = Typeface.DEFAULT
            option.background =
                ContextCompat.getDrawable(
                    this@QuizQuestionActivity,
                    R.drawable.default_option_border
                )
        }
    }

    private fun answerView(answer: Int, drawbleView: Int) {
        when (answer) {

            1 -> {
                tv_option_one.background =
                    ContextCompat.getDrawable(this@QuizQuestionActivity, drawbleView)
            }
            2 -> {
                tv_option_two.background =
                    ContextCompat.getDrawable(this@QuizQuestionActivity, drawbleView)
            }
            3 -> {
                tv_option_three.background =
                    ContextCompat.getDrawable(this@QuizQuestionActivity, drawbleView)
            }
            4 -> {
                tv_option_four.background =
                    ContextCompat.getDrawable(this@QuizQuestionActivity, drawbleView)
            }
            5 -> {
                tv_option_five.background =
                    ContextCompat.getDrawable(this@QuizQuestionActivity, drawbleView)
            }
        }
    }
}



