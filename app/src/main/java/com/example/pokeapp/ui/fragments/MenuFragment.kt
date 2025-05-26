package com.example.pokeapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.pokeapp.R
import com.example.pokeapp.ui.activities.AppActivity
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
        val btnGames = view.findViewById<ImageButton>(R.id.btn_games)
        val btnTeams = view.findViewById<ImageButton>(R.id.btn_teams)
        val btnHome = view.findViewById<ImageButton>(R.id.btn_home)

        val currentActivity = requireActivity()::class.java

        btnMaps.isEnabled = currentActivity != CompeticionActivity::class.java
        btnGames.isEnabled = currentActivity != GamesActivity::class.java
        btnTeams.isEnabled = currentActivity != EquipoActivity::class.java
        btnHome.isEnabled = currentActivity != AppActivity::class.java

        btnMaps.setOnClickListener {
            if (currentActivity != CompeticionActivity::class.java) {
                startActivity(Intent(requireContext(), CompeticionActivity::class.java))
            }
        }

        btnGames.setOnClickListener {
            if (currentActivity != GamesActivity::class.java) {
                startActivity(Intent(requireContext(), GamesActivity::class.java))
            }
        }

        btnTeams.setOnClickListener {
            if (currentActivity != EquipoActivity::class.java) {
                startActivity(Intent(requireContext(), EquipoActivity::class.java))
            }
        }

        btnHome.setOnClickListener {
            if (currentActivity != AppActivity::class.java) {
                startActivity(Intent(requireContext(), AppActivity::class.java))
            }
        }
    }
}
