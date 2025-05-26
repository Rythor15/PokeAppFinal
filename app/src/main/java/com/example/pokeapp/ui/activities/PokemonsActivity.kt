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

/*class PokemonsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonsBinding
    var pokeInfo = mutableListOf<PokemonApi>()
    var adapter = PokemonAdapter(pokeInfo)
    val layoutManager = GridLayoutManager(this,10)


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
            binding.rvGen1.layoutManager = layoutManager
            binding.rvGen1.adapter = adapter
            capturarPokemon()
    }
    private fun capturarPokemon() {
        val listaPokemonInfo = mutableListOf<PokemonApi>()
        val numeroPokes = 1302

        lifecycleScope.launch {
            for (i in 1..numeroPokes) {
                try {
                    // Obtenemos la información del Pokémon en el hilo IO
                    val pokemonInfo = withContext(Dispatchers.IO) {
                        apiClient.getPokemonInfo(i)
                    }

                    if (pokemonInfo != null) {
                        listaPokemonInfo.add(pokemonInfo)

                        // Actualizamos la UI inmediatamente después de recibir la respuesta
                        withContext(Dispatchers.Main) {
                            adapter.actualizarPokedex(listaPokemonInfo)
                        }
                        /*when(i){
                            151 -> {
                                binding.rvGen2.layoutManager = layoutManager
                                binding.rvGen2.adapter = adapter
                            }
                        }*/
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
        }
    }
}*/