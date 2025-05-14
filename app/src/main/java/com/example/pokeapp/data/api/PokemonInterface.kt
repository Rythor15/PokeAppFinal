package com.example.pokeapp.data.api

import com.example.pokeapp.data.PokemonApi
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonInterface {
    @GET("api/v2/pokemon/{id}")
    suspend fun getPokemonInfo(@Path("id") id: Int): PokemonApi
}