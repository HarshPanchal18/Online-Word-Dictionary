package com.example.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.DictionaryPageBinding
import com.example.dictionary.model.Word

class WordAdapter(private val items: MutableList<Word>): RecyclerView.Adapter<WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = DictionaryPageBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return WordViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentWord = items[position]

        holder.bind(currentWord)
    }

    override fun getItemCount(): Int = items.size
}
