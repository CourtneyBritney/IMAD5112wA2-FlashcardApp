package com.example.flashcards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.databinding.ItemReviewBinding

class ReviewAdapter(private val items: List<Pair<String, String>>) :
    RecyclerView.Adapter<ReviewAdapter.VH>() {

    class VH(val bind: ItemReviewBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return VH(ItemReviewBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val (q, a) = items[position]
        holder.bind.txtQuestion.text = q
        holder.bind.txtAnswer.text = a
    }
}
