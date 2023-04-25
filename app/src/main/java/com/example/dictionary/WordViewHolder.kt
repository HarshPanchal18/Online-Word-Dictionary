package com.example.dictionary

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.widget.TextView
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
            //sourceUrl.text = word.sourceUrls.toString().removePrefix("[").removeSuffix("]")
            licenseName.text = word.license.name
            //licenseUrl.text = word.license.url.removePrefix("[").removeSuffix("]")

            clickableText(word.license.url, licenseUrl)
            clickableText(word.sourceUrls.toString(), sourceUrl)

            val mergedDefinitions = StringBuilder()
            val mergedExamples = StringBuilder()

            val nounDefinitions = word.meanings.find { it.partOfSpeech == "noun" }?.definitions
            nounDefinitions?.forEachIndexed { index, definition ->
                mergedDefinitions.append("- " + definition.definition + "\n")
                if (definition.example != null)
                    mergedExamples.append(("- " + definition.example + "\n"))
            }

            nounDefinition.text = mergedDefinitions
            nounExample.text = mergedExamples
            nounAntonyms.text = word.meanings[0].antonyms.toString()
                .removeSurrounding("[", "]")
            nounSynonyms.text = word.meanings[0].synonyms.toString()
                .removeSurrounding("[", "]")

            mergedDefinitions.clear()
            mergedExamples.clear()

            val verbDefinitions = word.meanings.find { it.partOfSpeech == "verb" }?.definitions
            verbDefinitions?.forEachIndexed { index, definition ->
                mergedDefinitions.append("- " + definition.definition + "\n")
                if (definition.example != null)
                    mergedExamples.append(("- " + definition.example + "\n"))
            }

            verbDefinition.text = mergedDefinitions
            verbExample.text = mergedExamples
            verbAntonyms.text = word.meanings[1].antonyms.toString()
                .removeSurrounding("[", "]")
            verbSynonyms.text = word.meanings[1].synonyms.toString()
                .removeSurrounding("[", "]")
        }

    }

    private fun clickableText(longs: String, textView: TextView) {
        try {
            val spanned = SpannableString(longs)
            val matcher =
                Patterns.WEB_URL.matcher(longs) // where WEB_URL is a builtin regex function for grabbing out the Website
            var matchStart: Int
            var matchEnd: Int // both are for tracking cursor or index

            while (matcher.find()) { // enters if string contains any URL
                matchStart = matcher.start(1)
                matchEnd = matcher.end()

                var url = longs.substring(matchStart, matchEnd)
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "https:$url"

                val clickableSpan: ClickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        itemView.context.startActivity(intent)
                    }

                    override fun updateDrawState(ds: TextPaint) { // for customize the state of that link
                        super.updateDrawState(ds)
                        ds.color = Color.BLUE
                        //ds.isUnderlineText = false
                    }
                }
                spanned.setSpan(
                    clickableSpan,
                    matchStart,
                    matchEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                textView.text = spanned
                textView.movementMethod = LinkMovementMethod.getInstance()
            } // end of while()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } // end of clickable()

}
