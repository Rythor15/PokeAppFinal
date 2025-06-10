package com.example.pokeapp.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.pokeapp.R
import com.example.pokeapp.databinding.ActivityCompeticionBinding
import com.example.pokeapp.ui.fragments.MenuFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



// Clase de datos para contener la información del marcador, incluyendo el mes
data class Marcador(
    val coordenadas: LatLng,
    val titulo: String,
    val mes: String
)

class CompeticionActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityCompeticionBinding
    private val LOCATION_CODE = 10005
    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permisos ->
        if (permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ||
            permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            gestionarLocalizacion()
        } else {
            Toast.makeText(this, "El usuario denegó los permisos...", Toast.LENGTH_SHORT).show()
        }

    }

    private lateinit var map: GoogleMap
    private val marcadores = mutableListOf<Marcador>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompeticionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ponerMarcadores()
        iniciarFragmentMapa()
        iniciarFragmentMenu()
        personalizarSpinner()
        setListeners()
    }

    private fun setListeners() {
        binding.spinnerMeses.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedMonth = parent?.getItemAtPosition(position).toString().lowercase()
                filtrarMostrarMarcadores(selectedMonth)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.info.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Torneos")
                .setMessage("En esta pantalla podras informarte acerca de los torneos que hay sobre Pokemon competitivo.\n" +
                        "Para poder buscar la localización de un torneo según el mes que quieras tienes que pulsar sobre  (-- Selecciona un mes --)  y luego seleccionar el mes que quieras.")
                .setCancelable(false)
                .setPositiveButton("ACEPTAR", null)
                .create()
                .show()
        }
    }

    private fun personalizarSpinner() {
        val spinnerMeses: Spinner = binding.spinnerMeses
        val opcionesMeses = resources.getStringArray(R.array.array_meses)

        val adapterMeses = ArrayAdapter(
            this,
            R.layout.spinner_item,
            opcionesMeses
        )
        adapterMeses.setDropDownViewResource(R.layout.spinner_dropdown_item) // layout para el desplegable

        spinnerMeses.adapter = adapterMeses
    }

    private fun mensajesMarcadores() {
            map.setOnMarkerClickListener { marcador ->
                val value = marcador.title.toString()
                Toast.makeText(this, String.format(value), Toast.LENGTH_SHORT).show()
                true
            }
    }

    private fun iniciarFragmentMenu() {
        val fragmentMenu = MenuFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.menu_fragment, fragmentMenu)
        }
    }

    private fun iniciarFragmentMapa() {
        val fragmentMapa = SupportMapFragment()
        fragmentMapa.getMapAsync(this)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fm_maps, fragmentMapa)
        }
    }


    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map.uiSettings.isZoomControlsEnabled=true
        gestionarLocalizacion()
        filtrarMostrarMarcadores("-- Selecciona un mes --")
        mensajesMarcadores()
    }

    private fun ponerMarcador(coordenadas: LatLng, titulo: String) {
        val marker = MarkerOptions().position(coordenadas).title(titulo)
        map.addMarker(marker)
    }

    private fun ponerMarcadores() {
        marcadores.add(Marcador(LatLng(33.82475463077455, -117.90661939520065), " Mundial Pokemon 2025 California", "agosto"))
        marcadores.add(Marcador(LatLng(44.4949, 11.3426), " Regional Pokemon Bolonia, Italia", "mayo"))
        marcadores.add(Marcador(LatLng(-33.4489, -70.6693), " Regional Pokemon Santiago, Chile", "mayo"))
        marcadores.add(Marcador(LatLng(29.938928457154105, -90.06341077391745), " Internacional Pokemon Nueva Orleans", "junio"))
        marcadores.add(Marcador(LatLng(52.0907, 5.1214), " Regional Pokemon Utrecht, Paises Bajos", "mayo"))
        marcadores.add(Marcador(LatLng(43.0389, -87.9065), " Regional Pokemon Milwaukee, EE.UU", "mayo"))
        marcadores.add(Marcador(LatLng(45.5051, -122.6750), " Regional Pokemon Portland, EE.UU", "mayo"))
        marcadores.add(Marcador(LatLng(-37.8136, 144.9631), " Regional Pokemon Melbourne, Australia", "mayo"))
        marcadores.add(Marcador(LatLng(-29.7289, 31.0743), " Regional Pokemon Umhlanga Rocks, Sudáfrica", "mayo"))
        marcadores.add(Marcador(LatLng(33.82475463077455, -117.90661939520065), " Mundial Pokemon 2026 California", "agosto"))
    }

    private fun filtrarMostrarMarcadores (mes: String){
        map.clear()
        if (mes.lowercase() == "-- Selecciona un mes --") {
            marcadores.forEach {
                ponerMarcador(it.coordenadas, it.titulo)
            }
        } else {
            val filteredMarkers = marcadores.filter {
                it.mes.lowercase() == mes.lowercase()
            }
            filteredMarkers.forEach {
                ponerMarcador(it.coordenadas, it.titulo)
            }
            if (filteredMarkers.isNotEmpty()) {
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(filteredMarkers[0].coordenadas,10f),
                    3500,
                    null
                )
            }
            if (filteredMarkers.isEmpty()) {
                Toast.makeText(this, "No hay competiciones para ese mes", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun gestionarLocalizacion() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
        } else {
            pedirPermisos()
        }
    }


    private fun pedirPermisos() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
            ||
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            mostrarExplicacion()
        } else {
            escogerPermisos()
        }
    }

    private fun escogerPermisos() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun mostrarExplicacion() {
        AlertDialog.Builder(this)
            .setTitle("Permisos de Ubicacion")
            .setMessage("Debe aceptar los permisos de ubicación para usar la aplicación")
            .setNegativeButton("Cancelar") {
                    dialog, _ -> dialog.dismiss()
            }
            .setCancelable(false)
            .setPositiveButton("Aceptar") {
                    dialog , _ -> startActivity(Intent(Settings.ACTION_APPLICATION_SETTINGS))
                dialog.dismiss()
            }
            .create()
            .dismiss()

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        gestionarLocalizacion()
    }
}