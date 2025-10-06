package com.example.flashcards

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    private lateinit var ui: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)

        ui.txtFinalScore.text = getString(R.string.final_score_format, score, total)
        ui.txtFeedback.text = when {
            total == 0 -> getString(R.string.no_questions)
            score >= (0.8 * total) -> getString(R.string.great_job)
            score >= (0.5 * total) -> getString(R.string.not_bad)
            else -> getString(R.string.keep_practicing)
        }

        ui.btnRestart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        ui.btnReview.setOnClickListener {
            val texts = intent.getStringArrayExtra("q_texts") ?: emptyArray()
            val answers = intent.getBooleanArrayExtra("q_answers") ?: BooleanArray(0)
            val i = Intent(this, ReviewActivity::class.java).apply {
                putExtra("q_texts", texts)
                putExtra("q_answers", answers)
            }
            startActivity(i)
        }
    }
}
