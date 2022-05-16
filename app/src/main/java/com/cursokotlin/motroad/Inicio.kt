package com.cursokotlin.motroad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cursokotlin.motroad.R.id
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_inicio.*
import kotlinx.android.synthetic.main.activity_maps.*


class Inicio : AppCompatActivity() {

    companion object{
        const val REQUEST_CODE_LOCATION=0
        private val REQUEST_CODE_AUTOCOMPLETE_FROM = 1
        private val REQUEST_CODE_AUTOCOMPLETE_TO = 2

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        var recom:Button=findViewById(id.Recom)
        recom.setOnClickListener{
            startActivity(Intent(this, MapsActivity2::class.java))
        }

        var nuevo: Button = findViewById(id.nmap)
        Places.initialize(applicationContext, "@string/google_maps_key")
        btnAtras.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnInicio.setOnClickListener{
            startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_FROM)
        }
        btnDestino.setOnClickListener{
            startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_TO)
        }
        nuevo.setOnClickListener{
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    private fun startAutocomplete(requestCode: Int){


        // Create a new PlacesClient instance
        val fields = listOf(Place.Field.ID, Place.Field.NAME)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        startActivityForResult(intent, requestCode)
    }

}