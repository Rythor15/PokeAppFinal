package com.example.pokeapp.data

import com.google.gson.annotations.SerializedName

data class PokemonApi(
    @SerializedName("sprites") val pokemonSprite: PokemonSprite,
)

data class PokemonSprite(
    @SerializedName("front_default") val frontDefault: String,
    @SerializedName("front_shiny") val frontShiny: String,
)