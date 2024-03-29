package com.example.dictionary.model

data class Word(
    val word: String,
    val license: license,
    val meanings: List<meanings>,
    val phonetic: String,
    val origin: String?,
    val phonetics: List<phonetic>,
    val sourceUrls: List<String>,
)

data class license(
    val name: String,
    val url: String,
)

data class meanings(
    val antonyms: List<String>?,
    val definitions: List<definition>,
    val partOfSpeech: String,
    val synonyms: List<String>?,
)

data class phonetic(
    val audio: String,
    val license: license,
    val sourceUrl: String,
    val text: String,
)

data class definition(
    val antonyms: List<String>?,
    val definition: String,
    val example: String?,
    val synonyms: List<String>?,
)
