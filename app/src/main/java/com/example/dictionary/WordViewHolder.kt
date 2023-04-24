package com.example.dictionary

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.DictionaryPageBinding
import com.example.dictionary.model.Word
import com.example.dictionary.model.meanings

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

            val mergedDefinitions = StringBuilder()
            var mergedExamples = String()
            val nounDefinitions = word.meanings.find { it.partOfSpeech == "noun" }?.definitions
            nounDefinitions?.forEachIndexed { index, definition ->
                mergedDefinitions.append(definition.definition + "\n")
                mergedExamples += ((definition.example + "\n")
                    .ifEmpty { " " }).split("[",null.toString())
                    //.split(null.toString())
            }

            nounDefinition.text = mergedDefinitions
            nounExample.text = mergedExamples
                //.removePrefix("[")
                //.removeSurrounding("][","][")
            nounAntonyms.text = word.meanings[0].antonyms.toString()
                .removeSurrounding("[","]")
            nounSynonyms.text = word.meanings[0].synonyms.toString()
                .removeSurrounding("[","]")

            mergedDefinitions.clear()
            mergedExamples = ""

            val verbDefinitions = word.meanings.find { it.partOfSpeech == "verb" }?.definitions
            verbDefinitions?.forEachIndexed { index, definition ->
                mergedDefinitions.append(definition.definition + "\n")
                mergedExamples  += ((definition.example + "\n")
                    .ifEmpty { " " })
                    //.split("null",",")
            }

            verbDefinition.text = mergedDefinitions
            verbExample.text = mergedExamples
                //.removePrefix("[")
            verbAntonyms.text = word.meanings[1].antonyms.toString()
                .removeSurrounding("[","]")
            verbSynonyms.text = word.meanings[1].synonyms.toString()
                .removeSurrounding("[","]")
        }
    }

}
