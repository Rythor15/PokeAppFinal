package com.example.pokeapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
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
import java.util.ArrayList

class PokemonsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonsBinding
    private val selectedPokemon = mutableSetOf<PokemonEntity>()

    var adapterGen1 = PokemonAdapter(mutableListOf(), { pokemon, isSelected -> estaCompleto(pokemon, isSelected) }, selectedPokemon)
    var adapterGen2 = PokemonAdapter(mutableListOf(), { pokemon, isSelected -> estaCompleto(pokemon, isSelected) }, selectedPokemon)
    var adapterGen3 = PokemonAdapter(mutableListOf(), { pokemon, isSelected -> estaCompleto(pokemon, isSelected) }, selectedPokemon)
    var adapterGen4 = PokemonAdapter(mutableListOf(), { pokemon, isSelected -> estaCompleto(pokemon, isSelected) }, selectedPokemon)
    var adapterGen5 = PokemonAdapter(mutableListOf(), { pokemon, isSelected -> estaCompleto(pokemon, isSelected) }, selectedPokemon)
    var adapterGen6 = PokemonAdapter(mutableListOf(), { pokemon, isSelected -> estaCompleto(pokemon, isSelected) }, selectedPokemon)
    var adapterGen7 = PokemonAdapter(mutableListOf(), { pokemon, isSelected -> estaCompleto(pokemon, isSelected) }, selectedPokemon)
    var adapterGen8 = PokemonAdapter(mutableListOf(), { pokemon, isSelected -> estaCompleto(pokemon, isSelected) }, selectedPokemon)
    var adapterGen9 = PokemonAdapter(mutableListOf(), { pokemon, isSelected -> estaCompleto(pokemon, isSelected) }, selectedPokemon)

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
        setListeners()
    }

    private fun setListeners() {
        binding.btnCrearEquipo.setOnClickListener {
            if (selectedPokemon.size == 6) {
                val intent = Intent(this, EquipoActivity::class.java)
                // Convertir el MutableSet a ArrayList para pasarlo como Serializable
                intent.putExtra("equipo", ArrayList(selectedPokemon))
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona 6 Pokémon", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun estaCompleto(pokemon: PokemonEntity, isSelected: Boolean) {
        if (isSelected) {
            if (selectedPokemon.size < 6) {
                selectedPokemon.add(pokemon)
            } else {
                Toast.makeText(this, "Ya tienes 6 Pokémon en tu equipo", Toast.LENGTH_SHORT).show()
                // Si el límite de selección se alcanza, es posible que quieras revertir el estado visual del Pokémon que se intentó seleccionar.
                // Esto podría hacerse notificando al adaptador para que re-renderice ese elemento en particular.
                // Por ahora, solo se muestra un Toast.
            }
        } else {
            selectedPokemon.remove(pokemon)
        }
        Log.d("PokemonsActivity", "Selected Pokémon count: ${selectedPokemon.size}")
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
            adapterGen6.actualizarPokedex(db.pokemonDao().getGeneration(650, 721))
            adapterGen7.actualizarPokedex(db.pokemonDao().getGeneration(722, 809))
            adapterGen8.actualizarPokedex(db.pokemonDao().getGeneration(810, 898))
            adapterGen9.actualizarPokedex(db.pokemonDao().getGeneration(899, 1010))
        }
    }
}