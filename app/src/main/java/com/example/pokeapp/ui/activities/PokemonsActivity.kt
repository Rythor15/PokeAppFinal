package com.example.pokeapp.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokeapp.R
import com.example.pokeapp.data.PokemonApi
import com.example.pokeapp.data.api.ObjectApiPokemon.apiClient
import com.example.pokeapp.databinding.ActivityPokemonsBinding
import com.example.pokeapp.ui.adapters.PokemonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonsBinding
    var pokeInfo = mutableListOf<PokemonApi>()

    var adapterGen1 = PokemonAdapter(pokeInfo)
    var adapterGen2 = PokemonAdapter(pokeInfo)
    var adapterGen3 = PokemonAdapter(pokeInfo)
    var adapterGen4 = PokemonAdapter(pokeInfo)
    var adapterGen5 = PokemonAdapter(pokeInfo)
    var adapterGen6 = PokemonAdapter(pokeInfo)
    var adapterGen7 = PokemonAdapter(pokeInfo)
    var adapterGen8 = PokemonAdapter(pokeInfo)
    var adapterGen9 = PokemonAdapter(pokeInfo)

    val layoutManagerGen1 = GridLayoutManager(this,10)
    val layoutManagerGen2 = GridLayoutManager(this,10)
    val layoutManagerGen3 = GridLayoutManager(this,10)
    val layoutManagerGen4 = GridLayoutManager(this,10)
    val layoutManagerGen5 = GridLayoutManager(this,10)
    val layoutManagerGen6 = GridLayoutManager(this,10)
    val layoutManagerGen7 = GridLayoutManager(this,10)
    val layoutManagerGen8 = GridLayoutManager(this,10)
    val layoutManagerGen9 = GridLayoutManager(this,10)

    val generaciones = mapOf(
        "Gen 1" to 1..151,
        "Gen 2" to 152..251,
        "Gen 3" to 252..386,
        "Gen 4" to 387..493,
        "Gen 5" to 494..649,
        "Gen 6" to 650..721,
        "Gen 7" to 722..809,
        "Gen 8" to 810..898,
        "Gen 9" to 899..1010
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setRecycler()
    }

    private fun setRecycler() {
        binding.rvGen1.layoutManager = layoutManagerGen1
        binding.rvGen1.adapter = adapterGen1

        binding.rvGen2.layoutManager = layoutManagerGen2
        binding.rvGen2.adapter = adapterGen2

        binding.rvGen3.layoutManager = layoutManagerGen3
        binding.rvGen3.adapter = adapterGen3

        binding.rvGen4.layoutManager = layoutManagerGen4
        binding.rvGen4.adapter = adapterGen4

        binding.rvGen5.layoutManager = layoutManagerGen5
        binding.rvGen5.adapter = adapterGen5

        binding.rvGen6.layoutManager = layoutManagerGen6
        binding.rvGen6.adapter = adapterGen6

        binding.rvGen7.layoutManager = layoutManagerGen7
        binding.rvGen7.adapter = adapterGen7

        binding.rvGen8.layoutManager = layoutManagerGen8
        binding.rvGen8.adapter = adapterGen8

        binding.rvGen9.layoutManager = layoutManagerGen9
        binding.rvGen9.adapter = adapterGen9

        capturarPokemon()
    }
    private fun capturarPokemon() {

        lifecycleScope.launch {
            generaciones.forEach { (nombreGen, rango) ->
                    val listaPokemonInfo = mutableListOf<PokemonApi>()
                    for (i in rango) {
                        try {
                            // Obtenemos la información del Pokémon en el hilo IO
                            val pokemonInfo = withContext(Dispatchers.IO) {
                                apiClient.getPokemonInfo(i)
                            }
                            if (pokemonInfo != null) {
                                listaPokemonInfo.add(pokemonInfo)
                            } else {
                                Log.e("PokemonError", "El Pokémon con ID $i no se pudo obtener")
                            }
                        } catch (e: Exception) {
                            Log.e(
                                "PokemonError",
                                "Error al obtener el Pokémon con ID $i: ${e.message}"
                            )
                        }
                    }
                // Actualizamos la UI inmediatamente después de recibir la respuesta
                withContext(Dispatchers.Main) {
                    when (nombreGen) {
                        "Gen 1" -> adapterGen1.actualizarPokedex(listaPokemonInfo)
                        "Gen 2" -> adapterGen2.actualizarPokedex(listaPokemonInfo)
                        "Gen 3" -> adapterGen3.actualizarPokedex(listaPokemonInfo)
                        "Gen 4" -> adapterGen4.actualizarPokedex(listaPokemonInfo)
                        "Gen 5" -> adapterGen5.actualizarPokedex(listaPokemonInfo)
                        "Gen 6" -> adapterGen6.actualizarPokedex(listaPokemonInfo)
                        "Gen 7" -> adapterGen7.actualizarPokedex(listaPokemonInfo)
                        "Gen 8" -> adapterGen8.actualizarPokedex(listaPokemonInfo)
                        "Gen 9" -> adapterGen9.actualizarPokedex(listaPokemonInfo)
                    }

                }
            }
        }
    }
}