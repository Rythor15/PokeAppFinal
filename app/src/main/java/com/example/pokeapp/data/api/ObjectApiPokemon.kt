package com.example.pokeapp.data.api

object ObjectApiPokemon {
    private val retrofit2 = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //Con esto conseguimos que la variable pueda usar la interfaz sin necesidad de crear una instancia de la interfaz y demas
    val apiClient = retrofit2.create(PokemonInterfaz::class.java)
}