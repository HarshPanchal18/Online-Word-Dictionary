package com.example.dictionary

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.DictionaryPageBinding
import com.example.dictionary.model.Word

class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = DictionaryPageBinding.bind(itemView)

    fun bind(word: Word) {
        binding.apply {
            searchedword.text = word.word
            phonetic.text = word.phonetic
            origin.text = word.origin
            sourceUrl.text = word.sourceUrls.toString().removePrefix("[").removeSuffix("]")
            licenseName.text = word.license.name
            licenseUrl.text = word.license.url.removePrefix("[").removeSuffix("]")
        }
    }

}
