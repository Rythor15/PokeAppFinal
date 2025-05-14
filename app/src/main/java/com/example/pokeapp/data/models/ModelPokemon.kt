package com.example.pokeapp.data.models

import com.example.pokeapp.R
import java.io.Serializable

data class ModelPokemon(
    val entrenador: String,
    var pokemon1: String = "",
    var pokemon2: String = "",
    var pokemon3: String = "",
    var pokemon4: String = "",
    var pokemon5: String = "",
    var pokemon6: String = ""
): Serializable