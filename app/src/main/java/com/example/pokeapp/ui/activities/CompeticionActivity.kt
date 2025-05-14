package com.example.pokeapp.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.provider.Settings
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
        iniciarFramentMapa()
        iniciarFragmentMenu()
        personalizarSpinner()
    }

    private fun personalizarSpinner() {
        val spinnerAnios: Spinner = findViewById(R.id.spinner_anios)
        val spinnerMeses: Spinner = findViewById(R.id.spinner_meses)

        val opcionesAnios = resources.getStringArray(R.array.array_anios)
        val opcionesMeses = resources.getStringArray(R.array.array_meses)

        val adapterAnios = ArrayAdapter(
            this,
            R.layout.spinner_item,
            opcionesAnios
        )
        adapterAnios.setDropDownViewResource(R.layout.spinner_dropdown_item) // layout para el desplegable
        spinnerAnios.adapter = adapterAnios

        val adapterMeses = ArrayAdapter(
            this,
            R.layout.spinner_item,
            opcionesMeses
        )
        adapterMeses.setDropDownViewResource(R.layout.spinner_dropdown_item) // layout para el desplegable

        spinnerMeses.adapter = adapterMeses
    }

    private fun mensajesMarcadores() {
        map.setOnMarkerClickListener {
            var value = it.title.toString()
            when (value) {
                "Mundial Pokemon 2025" -> {
                    Toast.makeText(this, String.format(getString(R.string.texto_toast) + " " + value), Toast.LENGTH_SHORT).show()
                    true
                }
                "Regional Pokemon Europa Reino Unido" -> {
                    Toast.makeText(this, String.format(getString(R.string.texto_toast) + value), Toast.LENGTH_SHORT).show()
                    true
                }
                "Regional Pokemon Europa Suecia" -> {
                    Toast.makeText(this, String.format(getString(R.string.texto_toast) + value), Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    false
                }

            }
        }
    }

    private fun iniciarFragmentMenu() {
        val fragmentMenu = MenuFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.menu_fragment_mapa, fragmentMenu)
        }
    }

    private fun iniciarFramentMapa() {
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
        ponerMarcador(LatLng(33.82475463077455, -117.90661939520065), "Mundial Pokemon 2025")
        ponerMarcador(LatLng(52.44851528365187, -1.7185531875234843), "Regional Pokemon Europa Reino Unido")
        ponerMarcador(LatLng(59.277428636742854, 18.01492628216522), "Regional Pokemon Europa Suecia")
        mostrarLugarTorneo(LatLng(33.82475463077455, -117.90661939520065), 10f)
        mensajesMarcadores()
    }

    private fun ponerMarcador(coordenadas: LatLng, titulo: String) {
        val marker = MarkerOptions().position(coordenadas).title(titulo)
        map.addMarker(marker)
    }

    private fun mostrarLugarTorneo(coordenadas: LatLng, zoom: Float) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas,zoom),
            3500,
            null
        )
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
            .setMessage("Para el uso adecuado de esta increible aplicacion necesitamos permisos de ubicacion ")
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