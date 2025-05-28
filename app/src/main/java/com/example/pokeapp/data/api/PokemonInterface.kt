package com.example.pokeapp.data.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface PokemonInterface {
    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemonInfo(@Path("id") id: Int): Response<PokemonApi>
}

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemons WHERE id BETWEEN :start AND :end ORDER BY id")
    suspend fun getGeneration(start: Int, end: Int): List<PokemonEntity>
}

