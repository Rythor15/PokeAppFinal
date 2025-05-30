package com.example.pokeapp.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.pokeapp.R
import com.example.pokeapp.databinding.ActivityParejasBinding
import com.example.pokeapp.ui.JuegoParejas
import com.example.pokeapp.ui.fragments.MenuFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ParejasActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityParejasBinding
    private var fondos = mutableListOf(
        R.drawable.flareon,
        R.drawable.vaporeon,
        R.drawable.jolteon,
        R.drawable.espeon,
        R.drawable.umbreon,
        R.drawable.leafeon,
        R.drawable.glaceon,
        R.drawable.sylveon,
    )
    private var fondosDificil = mutableListOf(
        R.drawable.pikachu,
        R.drawable.eevee,
        R.drawable.venusaur,
        R.drawable.charizard,
        R.drawable.blastoise,
        R.drawable.squirtle,
        R.drawable.bulbasaur,
        R.drawable.charmander,
        R.drawable.mew,
        R.drawable.mewtwo
    )

    private lateinit var drawerLayout: DrawerLayout
    private var puntuacion = 0
    private var aciertos = 0
    private var bloqueo = false
    private var numeroPrimero = -1
    private var numeroSegundo = -2
    private var juego = JuegoParejas()
    private var tableroNormal = mutableListOf<ImageView>()
    private var tableroDificil = mutableListOf<ImageView>()
    private var primeraImagenPulsada : ImageView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParejasBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        drawerLayout = binding.drawerLayout
        inicializarTableros()
        iniciarJuego()
        setListernes()
        iniciarFragmentMenu()
    }

    private fun inicializarTableros() {
        tableroNormal = mutableListOf(
            binding.iv0n,
            binding.iv1n,
            binding.iv2n,
            binding.iv3n,
            binding.iv4n,
            binding.iv5n,
            binding.iv6n,
            binding.iv7n,
            binding.iv8n,
            binding.iv9n,
            binding.iv10n,
            binding.iv11n,
            binding.iv12n,
            binding.iv13n,
            binding.iv14n,
            binding.iv15n
        )

        tableroDificil = mutableListOf(
            binding.iv0,
            binding.iv1,
            binding.iv2,
            binding.iv3,
            binding.iv4,
            binding.iv5,
            binding.iv6,
            binding.iv7,
            binding.iv8,
            binding.iv9,
            binding.iv10,
            binding.iv11,
            binding.iv12,
            binding.iv13,
            binding.iv14,
            binding.iv15,
            binding.iv16,
            binding.iv17,
            binding.iv18,
            binding.iv19,
            binding.iv20,
            binding.iv21,
            binding.iv22,
            binding.iv23,
            binding.iv24,
            binding.iv25,
            binding.iv26,
            binding.iv27,
            binding.iv28,
            binding.iv29,
            binding.iv30,
            binding.iv31,
            binding.iv32,
            binding.iv33,
            binding.iv34,
            binding.iv35
        )
    }

    private fun iniciarFragmentMenu() {
        val fragmentMenu = MenuFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.menu_fragment, fragmentMenu)
        }
    }

    private fun setListernes() {
        tableroNormal.forEach {
            it.setOnClickListener(this)
        }
        tableroDificil.forEach {
            it.setOnClickListener(this)
        }
        binding.ivMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navigationViewParejas.menu.getItem(0).setOnMenuItemClickListener {
            iniciarJuego()
            if (binding.tableLayoutNormal.isVisible) {
                iniciarTablero(tableroNormal)
            } else if (binding.tableLayoutDificil.isVisible){
                iniciarTablero(tableroDificil)
            } else {
                AlertDialog.Builder(this)
                    .setTitle("ADVERTENCIA")
                    .setMessage("Debe seleccionar una dificultad para poder reiniciar el juego")
                    .setCancelable(false)
                    .setPositiveButton("ACEPTAR", null)
                    .create()
                    .show()
            }
            true
        }
        binding.btnNormal.setOnClickListener {
            if (fondos.size == 18) {
                fondos.removeAll(fondosDificil)
            }
            iniciarJuego()
            iniciarTablero(tableroNormal)
            binding.tableLayoutDificil.visibility = View.INVISIBLE
            binding.tableLayoutNormal.visibility = View.VISIBLE
        }
        binding.btnDificil.setOnClickListener {
            if (fondos.size == 8) {
                fondos.addAll(fondosDificil)
            }
            iniciarJuego()
            iniciarTablero(tableroDificil)
            binding.tableLayoutNormal.visibility = View.INVISIBLE
            binding.tableLayoutDificil.visibility = View.VISIBLE
        }
    }

    private fun iniciarTablero(tablero: List<ImageView>) {
            for (item in tablero) {
                item.scaleType = ImageView.ScaleType.CENTER_CROP
                item.setImageResource(R.drawable.cartapokemonatras)
                item.isEnabled = true
            }
        }

    private fun iniciarJuego() {
        puntuacion = 0
        aciertos = 0
        ponerPuntuacion()
        primeraImagenPulsada = null
        juego.desordenarTablero()
    }

    private fun ponerPuntuacion() {
        binding.tvPuntuacion.text = String.format(getString(R.string.puntuacion), puntuacion.toString())
    }

    override fun onClick(v: View?) {

        if (binding.tableLayoutDificil.isVisible) {
            for (i in tableroDificil.indices) {
                if (v == tableroDificil[i]) {
                    if (!bloqueo) {
                        jugarCasilla(i, tableroDificil)
                    }
                }
            }
        }

        if (binding.tableLayoutNormal.isVisible) {
            for (i in tableroNormal.indices) {
                if (v == tableroNormal[i]) {
                    if (!bloqueo) {
                        jugarCasilla(i, tableroNormal)
                    }
                }
            }
        }
    }

    private fun jugarCasilla(casilla: Int, tablero: List<ImageView>) {
        if (primeraImagenPulsada == null) {
            //destapamos la primera casilla, no tengo ninguna abierta
            primeraImagenPulsada = tablero[casilla]
            if(binding.tableLayoutNormal.isVisible){
                mostrarImagen(tablero[casilla], juego.tablero[casilla])
                numeroPrimero = juego.tablero[casilla]
            }
            if (binding.tableLayoutDificil.isVisible){
                mostrarImagen(tablero[casilla], juego.tableroDificil[casilla])
                numeroPrimero = juego.tableroDificil[casilla]
            }


        } else {
            //Ya tengo una abierta, abre la segunda haber si acierto
            bloqueo=true
            if(binding.tableLayoutNormal.isVisible){
                mostrarImagen(tablero[casilla], juego.tablero[casilla])
                numeroSegundo = juego.tablero[casilla]
            }
            if (binding.tableLayoutDificil.isVisible){
                mostrarImagen(tablero[casilla], juego.tableroDificil[casilla])
                numeroSegundo = juego.tableroDificil[casilla]
            }
            if (numeroPrimero == numeroSegundo) {
                //Hemos acertado la pareja
                aciertos++
                puntuacion+=5
                ponerPuntuacion()
                primeraImagenPulsada = null
                bloqueo=false
                if (aciertos == 8 && binding.tableLayoutNormal.isVisible) {
                    mostrarGanador()
                }
                if (aciertos == 18 && binding.tableLayoutDificil.isVisible) {
                    mostrarGanador()
                }
            } else {
                //No hemos acertado, mantendremos las dos imagenes un tiempo y luego las ocultamos
                lifecycleScope.launch(Dispatchers.Main) {
                    delay(1000)
                    ocultarImagen(primeraImagenPulsada!!)
                    ocultarImagen(tablero[casilla])
                    bloqueo = false
                    primeraImagenPulsada = null
                    puntuacion--
                    ponerPuntuacion()
                }
            }

        }
    }


    private fun mostrarGanador() {
        AlertDialog.Builder(this)
            .setTitle("ENHORABUENA")
            .setMessage("Ganaste el juego con una puntuación de: ${puntuacion.toString()} puntos")
            .setCancelable(false)
            .setPositiveButton("ACEPTAR", null)
            .create()
            .show()
    }

    //--------------------------------------------------------------------------------------------------
    private fun mostrarImagen(imagen: ImageView, fondoImagen: Int) {
        imagen.apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            setImageResource(fondos[fondoImagen])
            isEnabled = false
        }
    }

    //--------------------------------------------------------------------------------------------------
    private fun ocultarImagen(imagen: ImageView) {
        imagen.apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            setImageResource(R.drawable.cartapokemonatras)
            isEnabled = true
        }
    }
}