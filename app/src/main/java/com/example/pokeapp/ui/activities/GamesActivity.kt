package com.example.pokeapp.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.pokeapp.R
import com.example.pokeapp.databinding.ActivityGamesBinding
import com.example.pokeapp.ui.Dificultad
import com.example.pokeapp.ui.QuizManager
import com.example.pokeapp.ui.fragments.MenuFragment

class GamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        iniciarFragmentMenu()
        setListeners()
    }


    private fun setListeners() {
        binding.btnPokequiz.setOnClickListener {
            mostrarDialogo()
        }
        binding.btnPareja.setOnClickListener {
            startActivity(Intent(this, ParejasActivity::class.java))
        }
        binding.btnPokefill.setOnClickListener {
            startActivity(Intent(this, PkmnQuizActivity::class.java))
        }
    }

    private fun mostrarDialogo() {
        val difficulties = arrayOf("Principiante", "Líder de Gimnasio", "Maestro Pokémon")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona la dificultad")
        builder.setItems(difficulties) { dialog, which ->
            // 'which' es el índice de la opción seleccionada (0, 1, 2, etc.)
            val selectedDifficultyEnum = when (which) {
                0 -> Dificultad.PRINCIPIANTE
                1 -> Dificultad.LIDER_GIMNASIO
                2 -> Dificultad.MAESTRO_POKEMON
                else -> Dificultad.PRINCIPIANTE // Opción por defecto
            }

            // Iniciar la actividad del quiz y pasar la dificultad seleccionada
            lanzarActivity(selectedDifficultyEnum)

            dialog.dismiss() // Cierra el diálogo
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun lanzarActivity(dificultad: Dificultad) {
        val intent = Intent(this, PreguntasActivity::class.java) // Asumo que tu QuizActivity se llama MainActivity
        intent.putExtra("QUIZ_DIFFICULTY", dificultad.name) // Pasa el nombre del enum como String
        startActivity(intent)
    }

    private fun iniciarFragmentMenu() {
        val fragmentMenu = MenuFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.menu_fragment, fragmentMenu)
        }
    }

}