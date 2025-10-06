package com.example.flashcards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcards.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {
    private lateinit var ui: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val texts = intent.getStringArrayExtra("q_texts") ?: emptyArray()
        val answers = intent.getBooleanArrayExtra("q_answers") ?: BooleanArray(0)

        ui.recycler.layoutManager = LinearLayoutManager(this)
        ui.recycler.adapter = ReviewAdapter(texts.zip(answers).map {
            it.first to if (it.second) getString(R.string.true_word) else getString(R.string.false_word)
        })
    }
}
