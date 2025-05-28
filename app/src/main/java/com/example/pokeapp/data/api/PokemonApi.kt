package com.example.pokeapp.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.io.Serializable

data class PokemonApi(
    @SerializedName("sprites") val pokemonSprite: PokemonSprite,
    val id: Int,
    @SerializedName("name") val nombre: String,
    @SerializedName("types") val tipos: List<TiposPokemon>
)

data class TiposPokemon(
    val slot: Int,
    @SerializedName("type") val tipo: NombreTipo
)

data class NombreTipo(
    @SerializedName("name") val nombreTipo: String
)


data class PokemonSprite(
    @SerializedName("front_default") val frontDefault: String,
    @SerializedName("front_shiny") val frontShiny: String,
)

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val imageUrl: String,
    val generation: Int
): Serializable {
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






