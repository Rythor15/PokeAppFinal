package com.example.pokeapp.data.providers

import com.example.pokeapp.data.models.ModelEquipo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PokemonProvider {
    private val database = FirebaseDatabase.getInstance().getReference("EquiposPokemon")

    fun getDatos(datosEquipo: (MutableList<ModelEquipo>) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val equipo = mutableListOf<ModelEquipo>() // mutable list vacia
                for (item in snapshot.children) {
                    val valor = item.getValue(ModelEquipo::class.java)
                    if (valor != null) {
                        equipo.add(valor)
                    }
                }
                equipo.sortBy {
                    it.id
                }
                datosEquipo(equipo)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer el mensaje: ${error.message}")
            }

        })
    }
}