package com.example.flashcards

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.databinding.ActivityQuizBinding
import com.example.flashcards.model.Question

class QuizActivity : AppCompatActivity() {
    private lateinit var ui: ActivityQuizBinding

    private val questions = listOf(
        Question("The sun is a star.", true),
        Question("2 + 2 = 5.", false),
        Question("Cape Town is the capital of South Africa.", false),
        Question("Android apps can be written in Kotlin.", true),
        Question("A byte has 10 bits.", false)
    )

    private var index = 0
    private var score = 0
    private var answered = false
    private var lastAnswerCorrect = false

    companion object {
        private const val KEY_INDEX = "idx"
        private const val KEY_SCORE = "score"
        private const val KEY_ANSWERED = "answered"
        private const val KEY_LAST_CORRECT = "last_correct"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(ui.root)

        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(KEY_INDEX, 0)
            score = savedInstanceState.getInt(KEY_SCORE, 0)
            answered = savedInstanceState.getBoolean(KEY_ANSWERED, false)
            lastAnswerCorrect = savedInstanceState.getBoolean(KEY_LAST_CORRECT, false)
        }

        render()

        ui.btnTrue.setOnClickListener { handleAnswer(true) }
        ui.btnFalse.setOnClickListener { handleAnswer(false) }
        ui.btnNext.setOnClickListener { goNext() }
    }

    private fun render() {
        val q = questions[index]
        ui.txtQuestion.text = getString(R.string.question_format, index + 1, questions.size, q.text)
        ui.txtFeedback.text = if (!answered) "" else {
            if (lastAnswerCorrect) getString(R.string.correct) else getString(R.string.try_again)
        }
        ui.txtScore.text = getString(R.string.score_format, score, questions.size)
    }

    private fun handleAnswer(userSaidTrue: Boolean) {
        if (answered) {
            Toast.makeText(this, R.string.already_answered, Toast.LENGTH_SHORT).show()
            return
        }
        val correct = (questions[index].answerIsTrue == userSaidTrue)
        lastAnswerCorrect = correct
        if (correct) score++
        answered = true
        render()
    }

    private fun goNext() {
        if (!answered) {
            Toast.makeText(this, R.string.answer_first, Toast.LENGTH_SHORT).show()
            return
        }
        if (index < questions.lastIndex) {
            index++
            answered = false
            lastAnswerCorrect = false
            render()
        } else {
            val intent = Intent(this, ScoreActivity::class.java).apply {
                putExtra("score", score)
                putExtra("total", questions.size)
                putExtra("q_texts", questions.map { it.text }.toTypedArray())
                putExtra("q_answers", questions.map { it.answerIsTrue }.toBooleanArray())
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, index)
        outState.putInt(KEY_SCORE, score)
        outState.putBoolean(KEY_ANSWERED, answered)
        outState.putBoolean(KEY_LAST_CORRECT, lastAnswerCorrect)
    }
}
