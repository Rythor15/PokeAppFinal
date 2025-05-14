package com.example.pokeapp.data.providers

import com.example.pokeapp.data.models.ModelPokemon
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PokemonProvider {
    private val database = FirebaseDatabase.getInstance().getReference("EquipoPokemon")
    fun getDatos(datosAgenda: (MutableList<ModelPokemon>) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listado = mutableListOf<ModelPokemon>() // mutable list vacia
                for (item in snapshot.children) {
                    val valor = item.getValue(ModelPokemon::class.java)
                    if (valor != null) {
                        listado.add(valor)
                    }
                }
                listado.sortBy {
                    it.entrenador
                }
                datosAgenda(listado)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer el mensaje: ${error.message}")
            }

        })
    }
}