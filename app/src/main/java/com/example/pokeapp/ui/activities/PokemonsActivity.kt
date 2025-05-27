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
import com.example.pokeapp.data.api.PokemonApi
import com.example.pokeapp.data.api.ObjectApiPokemon.apiClient
import com.example.pokeapp.data.api.PokemonEntity
import com.example.pokeapp.data.db.AppDatabase
import com.example.pokeapp.databinding.ActivityPokemonsBinding
import com.example.pokeapp.ui.adapters.PokemonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonsBinding

    var adapterGen1 = PokemonAdapter(mutableListOf())
    var adapterGen2 = PokemonAdapter(mutableListOf())
    var adapterGen3 = PokemonAdapter(mutableListOf())
    var adapterGen4 = PokemonAdapter(mutableListOf())
    var adapterGen5 = PokemonAdapter(mutableListOf())
    var adapterGen6 = PokemonAdapter(mutableListOf())
    var adapterGen7 = PokemonAdapter(mutableListOf())
    var adapterGen8 = PokemonAdapter(mutableListOf())
    var adapterGen9 = PokemonAdapter(mutableListOf())

    val layoutManagerGen1 = GridLayoutManager(this,8)
    val layoutManagerGen2 = GridLayoutManager(this,8)
    val layoutManagerGen3 = GridLayoutManager(this,8)
    val layoutManagerGen4 = GridLayoutManager(this,8)
    val layoutManagerGen5 = GridLayoutManager(this,8)
    val layoutManagerGen6 = GridLayoutManager(this,8)
    val layoutManagerGen7 = GridLayoutManager(this,8)
    val layoutManagerGen8 = GridLayoutManager(this,8)
    val layoutManagerGen9 = GridLayoutManager(this,8)


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

        cargarDesdeRoom()
    }

    private fun cargarDesdeRoom() {
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            adapterGen1.actualizarPokedex(db.pokemonDao().getGeneration(1, 151))
            adapterGen2.actualizarPokedex(db.pokemonDao().getGeneration(152, 251))
            adapterGen3.actualizarPokedex(db.pokemonDao().getGeneration(252, 386))
            adapterGen4.actualizarPokedex(db.pokemonDao().getGeneration(387, 493))
            adapterGen5.actualizarPokedex(db.pokemonDao().getGeneration(494, 649))
            Log.d("GEN5", "")
            adapterGen6.actualizarPokedex(db.pokemonDao().getGeneration(650, 721))
            Log.d("GEN6", "Se generan")
            adapterGen7.actualizarPokedex(db.pokemonDao().getGeneration(722, 809))
            Log.d("GEN7", "Se generan tambien")
            adapterGen8.actualizarPokedex(db.pokemonDao().getGeneration(810, 898))
            adapterGen9.actualizarPokedex(db.pokemonDao().getGeneration(899, 1010))

        }
    }

}