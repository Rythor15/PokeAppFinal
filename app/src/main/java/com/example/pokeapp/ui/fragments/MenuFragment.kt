package com.example.pokeapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.commit
import com.example.pokeapp.R
import com.example.pokeapp.ui.activities.CompeticionActivity
import com.example.pokeapp.ui.activities.EquipoActivity
import com.example.pokeapp.ui.activities.GamesActivity

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnMaps = view.findViewById<ImageButton>(R.id.btn_maps)
        val btnGames = view.findViewById<ImageButton>(R.id.btm_games)
        val btnTeams = view.findViewById<ImageButton>(R.id.btn_teams)

        btnMaps.setOnClickListener {
            startActivity(Intent(requireContext(), CompeticionActivity::class.java))
        }

        btnGames.setOnClickListener {
            startActivity(Intent(requireContext(), GamesActivity::class.java))
        }

        btnTeams.setOnClickListener {
            startActivity(Intent(requireContext(), EquipoActivity::class.java))
        }
    }
}
