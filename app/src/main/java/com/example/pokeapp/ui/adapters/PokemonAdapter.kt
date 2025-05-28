package com.example.pokeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.data.api.PokemonEntity
import com.example.pokeapp.databinding.LayoutPokemonBinding
import com.squareup.picasso.Picasso
import com.example.pokeapp.data.api.TiposPokemon
import com.example.pokeapp.data.api.NombreTipo

class PokemonAdapter(
    var pokeInfo: MutableList<PokemonEntity>,
    val onPokemonSelected: (PokemonEntity, Boolean) -> Unit,
    val selectedPokemon: MutableSet<PokemonEntity>
) : RecyclerView.Adapter<ViewHolderPokemon>() {

    fun actualizarPokedex(newList: List<PokemonEntity>) {
        pokeInfo.clear()
        pokeInfo.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPokemon {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_pokemon, parent, false)
        return ViewHolderPokemon(v, onPokemonSelected, selectedPokemon)
    }

    override fun onBindViewHolder(holder: ViewHolderPokemon, position: Int) {
        val pokemon = pokeInfo[position]
        holder.render(pokemon, selectedPokemon.contains(pokemon))
    }

    override fun getItemCount() = pokeInfo.size
}

class ViewHolderPokemon(v: View, val onPokemonSelected: (PokemonEntity, Boolean) -> Unit, val selectedPokemon: MutableSet<PokemonEntity>) : RecyclerView.ViewHolder(v) {
    val binding = LayoutPokemonBinding.bind(v)

    fun render(pokemon: PokemonEntity, isSelected: Boolean) {
        Picasso.get().cancelRequest(binding.imgPokemon)
        Picasso.get().load(pokemon.imageUrl).into(binding.imgPokemon)
        //binding.imgPokemon.alpha = if (isSelected) 0.5f else 1.0f
            itemView.setOnClickListener {
                val estaSeleccionado = !selectedPokemon.contains(pokemon)
                onPokemonSelected(pokemon, estaSeleccionado)
                binding.imgPokemon.alpha = if (estaSeleccionado) 0.5f else 1.0f
            }
    }
}
