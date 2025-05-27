package com.example.pokeapp.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PokemonApi(
    @SerializedName("sprites") val pokemonSprite: PokemonSprite,
    val id: Int
)

data class PokemonSprite(
    @SerializedName("front_default") val frontDefault: String,
    @SerializedName("front_shiny") val frontShiny: String,
)

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val generation: Int

) {
    companion object {
        fun fromApi(api: PokemonApi, generation: Int): PokemonEntity {
            return PokemonEntity(
                id = api.id,
                imageUrl = api.pokemonSprite.frontDefault,
                generation = generation
            )
        }
    }

}




