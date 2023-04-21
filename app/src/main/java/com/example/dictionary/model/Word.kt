package com.example.dictionary.model

data class Word(
    val word: String,
    val phonetic: String,
    val origin: String,
    val meanings: ArrayList<partOfSpeech>
)

data class partOfSpeech(
    val exclamation: ArrayList<definitions>,
    val noun: ArrayList<definitions>,
    val verb: ArrayList<definitions>,
)

data class definitions(
    val definition: String,
    val example: String,
    val synonyms: String,
    val antonyms: String,
)
