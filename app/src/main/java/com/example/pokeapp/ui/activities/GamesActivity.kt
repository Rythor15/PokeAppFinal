package com.example.pokeapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.pokeapp.R
import com.example.pokeapp.databinding.ActivityGamesBinding
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
        binding.btnCandycrush.setOnClickListener {
            startActivity(Intent(this, CandyActivity::class.java))
        }
        binding.btnPareja.setOnClickListener {
            startActivity(Intent(this, ParejasActivity::class.java))
        }
        binding.btnPokequiz.setOnClickListener {
            startActivity(Intent(this, PkmnQuizActivity::class.java))
        }
    }

    private fun iniciarFragmentMenu() {
        val fragmentMenu = MenuFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.menu_fragment, fragmentMenu)
        }
    }

}