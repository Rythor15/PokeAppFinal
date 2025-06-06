package com.example.pokeapp.data.providers

import com.example.pokeapp.data.models.ModelEquipo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PokemonProvider {
    private val database = FirebaseDatabase.getInstance().getReference("EquiposPokemon")

    fun getDatos(datosEquipo: (List<ModelEquipo>) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid // Get current user's UID

        if (userId == null) {
            datosEquipo(emptyList()) // No user  logged in, return empty list
            return
        }

        database.orderByChild("userId").equalTo(userId) // Filter by userId
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val equipos = mutableListOf<ModelEquipo>()
                    for (equipoSnapshot in snapshot.children) {
                        val equipo = equipoSnapshot.getValue(ModelEquipo::class.java)
                        equipo?.let {
                            equipos.add(it)
                        }
                    }
                    datosEquipo(equipos)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                    datosEquipo(emptyList())
                }
            })
    }
}