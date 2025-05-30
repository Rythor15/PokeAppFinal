package com.example.pokeapp.data.models

data class PreguntaModel(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val isTrueFalse: Boolean = false
)
