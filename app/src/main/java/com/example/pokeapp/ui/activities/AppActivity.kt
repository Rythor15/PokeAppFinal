package com.example.pokeapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.pokeapp.R
import com.example.pokeapp.databinding.ActivityAppBinding
import com.example.pokeapp.ui.fragments.MenuFragment
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    private lateinit var auth: FirebaseAuth
    private var nombre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        iniciarFragment()
        auth = Firebase.auth
        setListeners()
    }

    private fun iniciarFragment() {
        val fragment = MenuFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.menu_fragment, fragment)
        }
    }

    private fun setListeners() {
        binding.navigationView.menu.getItem(0).setOnMenuItemClickListener {
            auth.signOut()
            finish()
            true
        }
        binding.navigationView.menu.getItem(1).setOnMenuItemClickListener {
            finishAffinity()
            true
        }
    }
}