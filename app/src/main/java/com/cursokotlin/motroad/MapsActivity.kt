package com.cursokotlin.motroad

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.cursokotlin.motroad.databinding.ActivityMapsBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_inicio.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    companion object{
        const val REQUEST_CODE_LOCATION=0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the SDK

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera 4.606581081646122, -74.06780094730827
        createMarker()
        enableLocation()
    }


    private fun createMarker(){
        val cordenadas = LatLng(4.606581081646122, -74.06780094730827)
        val marker=MarkerOptions().position(cordenadas).title("Mi U")
        mMap.addMarker(marker)
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(cordenadas,15f),
            3000,
            null
        )
    }

     private fun isLocationPermissionGranted()= ContextCompat.checkSelfPermission(
         this,
         Manifest.permission.ACCESS_FINE_LOCATION
     )==PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if(!::mMap.isInitialized) return
        if(isLocationPermissionGranted()){
            mMap.isMyLocationEnabled=true
        }else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this,"Primero debe aceptar los permisos en sus ajustes",Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                mMap.isMyLocationEnabled==true
            }else{
                Toast.makeText(this,"Para activar la localización, primero debe aceptar los permisos en sus ajustes",Toast.LENGTH_SHORT).show()
            }else -> { }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::mMap.isInitialized) return

        if(!isLocationPermissionGranted()){
            mMap.isMyLocationEnabled=false
            Toast.makeText(this,"Para activar la localización, primero debe aceptar los permisos en sus ajustes",Toast.LENGTH_SHORT).show()

        }
    }
}