package com.example.pokeapp.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokeapp.R
import com.example.pokeapp.data.api.ObjectApiPokemon.apiClient
import com.example.pokeapp.data.api.PokemonApi
import com.example.pokeapp.data.api.PokemonEntity
import com.example.pokeapp.data.models.ModelEquipo
import com.example.pokeapp.data.providers.PokemonProvider
import com.example.pokeapp.databinding.ActivityEquipoBinding
import com.example.pokeapp.ui.adapters.EquipoAdapter
import com.example.pokeapp.ui.fragments.MenuFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.ArrayList

class EquipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEquipoBinding
    private lateinit var equipoAdapter: EquipoAdapter
    private val pokemonProvider = PokemonProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEquipoBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        iniciarFragmentMenu()
        setListener()
        setRecycler()
        cargarEquipos()
        cargarNombreEntrenador()
    }

    private fun cargarNombreEntrenador() {
        val sharedPref = getSharedPreferences("pokeapp_prefs", Context.MODE_PRIVATE)
        val nombreEntrenador = sharedPref.getString("NOMBRE_ENTRENADOR", "") // Default value
        binding.nombreEntrenador.text = getString(R.string.nombreEntrenadorTV, nombreEntrenador)

    }

    private fun setListener() {
        binding.btnPokemons.setOnClickListener {
            startActivity(Intent(this, PokemonsActivity::class.java))
        }

        val selectedPokemonList = intent.getSerializableExtra("equipo") as? ArrayList<PokemonEntity>
        if (selectedPokemonList != null && selectedPokemonList.size == 6) {
            saveEquipoToFirebase(selectedPokemonList)
            // Una vez que se ha guardado, se puede limpiar el extra para evitar guardados duplicados
            intent.removeExtra("equipo")
        }
    }

    private fun saveEquipoToFirebase(selectedPokemon: List<PokemonEntity>) {
        val database = FirebaseDatabase.getInstance().getReference("EquiposPokemon")
        val equipoId = database.push().key ?: return

        val userId = FirebaseAuth.getInstance().currentUser?.uid // Get current user's UID
        if (userId == null) {
            Toast.makeText(this, "Error: Usuario no autenticado.", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val fullPokemonData = mutableListOf<PokemonApi>()
            for (pokemonEntity in selectedPokemon) {
                try {
                    // Realiza la llamada a la API para obtener los detalles completos del Pokémon
                    val response = apiClient.getPokemonInfo(pokemonEntity.id)
                    if (response.isSuccessful) {
                        response.body()?.let { fullPokemonData.add(it) }
                    } else {
                        Log.e("EquipoActivity", "Error al obtener detalles de Pokémon ${pokemonEntity.id}: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("EquipoActivity", "Excepción al obtener detalles de Pokémon ${pokemonEntity.id}: ${e.message}")
                }
            }

            // Crear el ModelEquipo con los datos recopilados
            val modelEquipo = ModelEquipo(id = (System.currentTimeMillis() % 1000000).toInt())
            modelEquipo.firebaseKey = equipoId
            modelEquipo.userId = userId

            selectedPokemon.forEachIndexed { index, pokemonEntity ->
                val fullData = fullPokemonData.find { it.id == pokemonEntity.id }
                when (index) {
                    0 -> {
                        modelEquipo.pokemon1 = fullData?.nombre?.replaceFirstChar { it.uppercaseChar() } ?: ""
                        modelEquipo.imgPokemon1 = pokemonEntity.imageUrl
                        modelEquipo.imgPokemon1Tipo1 = fullData?.tipos?.getOrNull(0)?.tipo?.nombreTipo ?: ""
                        modelEquipo.imgPokemon1Tipo2 = fullData?.tipos?.getOrNull(1)?.tipo?.nombreTipo ?: ""
                    }
                    1 -> {
                        modelEquipo.pokemon2 = fullData?.nombre?.replaceFirstChar { it.uppercaseChar() } ?: ""
                        modelEquipo.imgPokemon2 = pokemonEntity.imageUrl
                        modelEquipo.imgPokemon2Tipo1 = fullData?.tipos?.getOrNull(0)?.tipo?.nombreTipo ?: ""
                        modelEquipo.imgPokemon2Tipo2 = fullData?.tipos?.getOrNull(1)?.tipo?.nombreTipo ?: ""
                    }
                    2 -> {
                        modelEquipo.pokemon3 = fullData?.nombre?.replaceFirstChar { it.uppercaseChar() } ?: ""
                        modelEquipo.imgPokemon3 = pokemonEntity.imageUrl
                        modelEquipo.imgPokemon3Tipo1 = fullData?.tipos?.getOrNull(0)?.tipo?.nombreTipo ?: ""
                        modelEquipo.imgPokemon3Tipo2 = fullData?.tipos?.getOrNull(1)?.tipo?.nombreTipo ?: ""
                    }
                    3 -> {
                        modelEquipo.pokemon4 = fullData?.nombre?.replaceFirstChar { it.uppercaseChar() } ?: ""
                        modelEquipo.imgPokemon4 = pokemonEntity.imageUrl
                        modelEquipo.imgPokemon4Tipo1 = fullData?.tipos?.getOrNull(0)?.tipo?.nombreTipo ?: ""
                        modelEquipo.imgPokemon4Tipo2 = fullData?.tipos?.getOrNull(1)?.tipo?.nombreTipo ?: ""
                    }
                    4 -> {
                        modelEquipo.pokemon5 = fullData?.nombre?.replaceFirstChar { it.uppercaseChar() } ?: ""
                        modelEquipo.imgPokemon5 = pokemonEntity.imageUrl
                        modelEquipo.imgPokemon5Tipo1 = fullData?.tipos?.getOrNull(0)?.tipo?.nombreTipo ?: ""
                        modelEquipo.imgPokemon5Tipo2 = fullData?.tipos?.getOrNull(1)?.tipo?.nombreTipo ?: ""
                    }
                    5 -> {
                        modelEquipo.pokemon6 = fullData?.nombre?.replaceFirstChar { it.uppercaseChar() } ?: ""
                        modelEquipo.imgPokemon6 = pokemonEntity.imageUrl
                        modelEquipo.imgPokemon6Tipo1 = fullData?.tipos?.getOrNull(0)?.tipo?.nombreTipo ?: ""
                        modelEquipo.imgPokemon6Tipo2 = fullData?.tipos?.getOrNull(1)?.tipo?.nombreTipo ?: ""
                    }
                }
            }

            try {
                database.child(equipoId).setValue(modelEquipo).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EquipoActivity, "Equipo guardado correctamente!", Toast.LENGTH_SHORT).show()
                    cargarEquipos()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EquipoActivity, "Error al guardar el equipo: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarEquipos() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Toast.makeText(this, "Error: Usuario no autenticado para cargar equipos.", Toast.LENGTH_SHORT).show()
            equipoAdapter.updateEquipos(emptyList()) // Clear teams if no user is logged in
            return
        }
        pokemonProvider.getDatos { equipos ->
            val userEquipos = equipos.filter { it.userId == userId }
            equipoAdapter.updateEquipos(userEquipos)
        }
    }

    private fun setRecycler() {
        equipoAdapter = EquipoAdapter(
            mutableListOf(),
            onDeleteClick = { equipo -> onDeleteEquipo(equipo) }
        )

        binding.rvEquipo.layoutManager = GridLayoutManager(this,1)
        binding.rvEquipo.adapter = equipoAdapter
    }

    private fun iniciarFragmentMenu() {
        val fragmentMenu = MenuFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.menu_fragment, fragmentMenu)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, AppActivity::class.java)
        // Añade FLAG_ACTIVITY_CLEAR_TOP para limpiar la pila de actividades hasta AppActivity
        // y FLAG_ACTIVITY_NEW_TASK para iniciarla como una nueva tarea si no está en la pila
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun onDeleteEquipo(equipo: ModelEquipo) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de que quieres borrar el equipo?")
            .setPositiveButton("Sí") { dialog, _ ->
                deleteEquipoFirebase(equipo)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteEquipoFirebase(equipo: ModelEquipo) {
        val database = FirebaseDatabase.getInstance().getReference("EquiposPokemon")

        if (equipo.firebaseKey.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    database.child(equipo.firebaseKey).removeValue().await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EquipoActivity, "Equipo eliminado correctamente.", Toast.LENGTH_SHORT).show()
                        cargarEquipos() // Recargar la lista después de borrar
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EquipoActivity, "Error al eliminar equipo: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this@EquipoActivity, "No se pudo eliminar el equipo: clave de Firebase no encontrada.", Toast.LENGTH_SHORT).show()
        }
    }
}