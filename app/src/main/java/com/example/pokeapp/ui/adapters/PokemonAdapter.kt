package com.example.pokeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.data.PokemonApi
import com.example.pokeapp.databinding.LayoutPokemonBinding
import com.squareup.picasso.Picasso

class PokemonAdapter (
    var pokeInfo: List<PokemonApi>
):  RecyclerView.Adapter<ViewHolderPokemon>() {

    fun actualizarPokedex(newList: List<PokemonApi>) {
        pokeInfo = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPokemon {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.layout_pokemon, parent, false)
        return ViewHolderPokemon(v)
    }

    override fun onBindViewHolder(holder: ViewHolderPokemon, position: Int) {
        val pokemonInfo = pokeInfo[position]
        holder.render(pokemonInfo)
    }

    override fun getItemCount() = pokeInfo.size

}

class ViewHolderPokemon(v: View): RecyclerView.ViewHolder(v){
    val binding = LayoutPokemonBinding.bind(v)

    fun render(pokemonInfo: PokemonApi){
        Picasso.get().load(pokemonInfo.pokemonSprite.frontDefault).into(binding.imgPokemon)
    }
}