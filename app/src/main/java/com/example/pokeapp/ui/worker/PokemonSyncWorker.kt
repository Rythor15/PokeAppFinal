package com.example.pokeapp.ui.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokeapp.data.api.ObjectApiPokemon.apiClient
import com.example.pokeapp.data.api.PokemonEntity
import com.example.pokeapp.data.db.AppDatabase

class PokemonSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {



    override suspend fun doWork(): Result {
        val db = AppDatabase.getDatabase(applicationContext)
        val dao = db.pokemonDao()
        try {
            val generaciones = mapOf(
                1 to 1..151,
                2 to 152..251,
                3 to 252..386,
                4 to 387..493,
                5 to 494..649,
                6 to 650..721,
                7 to 722..809,
                8 to 810..898,
                9 to 899..1010
            )

            generaciones.forEach { (gen, rango) ->
                rango.forEach { id ->
                    val response = apiClient.getPokemonInfo(id)
                    if (response.isSuccessful) {
                        val pokemonApi = response.body()
                        if (pokemonApi != null) {
                            dao.insertPokemon(PokemonEntity.fromApi(pokemonApi, gen))
                        } else {
                            Log.e("Worker", "Cuerpo de la respuesta de Pokémon ${id} es nulo.")
                        }
                    } else {
                        Log.e("Worker", "Error de API al obtener Pokémon ${id}: ${response.code()}")
                    }
                }
            }

            return Result.success()

        } catch (e: Exception) {
            Log.e("Worker", "Error en sync: ${e.message}")
            return Result.failure()
        }
    }
}
