package com.example.pokeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.data.api.PokemonEntity
import com.example.pokeapp.data.models.ModelEquipo
import com.example.pokeapp.databinding.CvEquiposBinding
import com.squareup.picasso.Picasso

class EquipoAdapter(
    var equipos: MutableList<ModelEquipo>,
    //val onEditClick: (ModelEquipo) -> Unit,
    //val onDeleteClick: (ModelEquipo) -> Unit
) : RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder>() {

    fun updateEquipos(newEquipos: List<ModelEquipo>) {
        equipos.clear()
        equipos.addAll(newEquipos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cv_equipos, parent, false)
        return EquipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipo = equipos[position]
        holder.render(equipo)
    }

    override fun getItemCount() = equipos.size

    class EquipoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = CvEquiposBinding.bind(v)

        fun render(equipo: ModelEquipo) {
            // Nombres de Pokémon
            binding.tvPokemon1.text = equipo.pokemon1
            binding.tvPokemon2.text = equipo.pokemon2
            binding.tvPokemon3.text = equipo.pokemon3
            binding.tvPokemon4.text = equipo.pokemon4
            binding.tvPokemon5.text = equipo.pokemon5
            binding.tvPokemon6.text = equipo.pokemon6

            // Imágenes de Pokémon
            Picasso.get().load(equipo.imgPokemon1).into(binding.ivPokemon1)
            Picasso.get().load(equipo.imgPokemon2).into(binding.ivPokemon2)
            Picasso.get().load(equipo.imgPokemon3).into(binding.ivPokemon3)
            Picasso.get().load(equipo.imgPokemon4).into(binding.ivPokemon4)
            Picasso.get().load(equipo.imgPokemon5).into(binding.ivPokemon5)
            Picasso.get().load(equipo.imgPokemon6).into(binding.ivPokemon6)

            // Imágenes de Tipo
            loadImageType(equipo.imgPokemon1Tipo1, binding.ivPokemon1Tipo1)
            loadImageType(equipo.imgPokemon1Tipo2, binding.ivPokemon1Tipo2)
            loadImageType(equipo.imgPokemon2Tipo1, binding.ivPokemon2Tipo1)
            loadImageType(equipo.imgPokemon2Tipo2, binding.ivPokemon2Tipo2)
            loadImageType(equipo.imgPokemon3Tipo1, binding.ivPokemon3Tipo1)
            loadImageType(equipo.imgPokemon3Tipo2, binding.ivPokemon3Tipo2)
            loadImageType(equipo.imgPokemon4Tipo1, binding.ivPokemon4Tipo1)
            loadImageType(equipo.imgPokemon4Tipo2, binding.ivPokemon4Tipo2)
            loadImageType(equipo.imgPokemon5Tipo1, binding.ivPokemon5Tipo1)
            loadImageType(equipo.imgPokemon5Tipo2, binding.ivPokemon5Tipo2)
            loadImageType(equipo.imgPokemon6Tipo1, binding.ivPokemon6Tipo1)
            loadImageType(equipo.imgPokemon6Tipo2, binding.ivPokemon6Tipo2)
        }

        private fun loadImageType(typeName: String, imageView: ImageView) {
            if (typeName.isNotEmpty()) {
                when (typeName) {
                    "steel" -> Picasso.get().load(R.drawable.acero).into(imageView)
                    "water" -> Picasso.get().load(R.drawable.agua).into(imageView)
                    "bug" -> Picasso.get().load(R.drawable.bicho).into(imageView)
                    "dragon" -> Picasso.get().load(R.drawable.dragon).into(imageView)
                    "electric" -> Picasso.get().load(R.drawable.electrico).into(imageView)
                    "ghost" -> Picasso.get().load(R.drawable.fantasma).into(imageView)
                    "fire" -> Picasso.get().load(R.drawable.fuego).into(imageView)
                    "fairy" -> Picasso.get().load(R.drawable.hada).into(imageView)
                    "ice" -> Picasso.get().load(R.drawable.hielo).into(imageView)
                    "fighting" -> Picasso.get().load(R.drawable.lucha).into(imageView)
                    "normal" -> Picasso.get().load(R.drawable.normal).into(imageView)
                    "grass" -> Picasso.get().load(R.drawable.planta).into(imageView)
                    "psychic" -> Picasso.get().load(R.drawable.psiquico).into(imageView)
                    "rock" -> Picasso.get().load(R.drawable.roca).into(imageView) //rock?
                    "dark" -> Picasso.get().load(R.drawable.siniestro).into(imageView)
                    "ground" -> Picasso.get().load(R.drawable.tierra).into(imageView)
                    "poison" -> Picasso.get().load(R.drawable.veneno).into(imageView)
                    "flying" -> Picasso.get().load(R.drawable.volador).into(imageView)
                }
            } else {
                imageView.setImageDrawable(null) // Limpiar imagen si typeName está vacío
            }
        }
    }
}