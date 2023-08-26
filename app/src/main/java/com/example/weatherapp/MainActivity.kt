package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var placesClient: PlacesClient
    lateinit var binding:ActivityMainBinding
    private var mLatitude:Double=0.0
    private var mLongitude:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(!Places.isInitialized()){
            Places.initialize(this@MainActivity,resources.getString(R.string.google_maps_api_key)
            )

        }
        placesClient = Places.createClient(this)



        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }


        binding.btnCurrent.setOnClickListener {

            val placeFields: List<Place.Field> = listOf(Place.Field.NAME,Place.Field.LAT_LNG)

// Use the builder to create a FindCurrentPlaceRequest.
            val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)

// Call findCurrentPlace and handle the response (first check that the user has granted permission).
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

                val placeResponse = placesClient.findCurrentPlace(request)
                placeResponse.addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val response = task.result
                        for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods ?: emptyList()) {

                            Log.i(
                                "current latitude",
                                "Place '${placeLikelihood.place.latLng.latitude}'"
                            )
                            Log.i(
                                "current Longitude",
                                "Place '${placeLikelihood.place.latLng.longitude}'"
                            )
                            mLatitude = placeLikelihood.place.latLng.latitude
                            mLongitude = placeLikelihood.place.latLng.longitude
                            break
                        }

                    } else {
                        val exception = task.exception
                        if (exception is ApiException) {
                            Log.e("Location Status", "Place not found: ${exception.statusCode}")
                        }
                    }
                }
            } else {
                Log.e("Location Status", "Permission not found")

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))

            }

        }
    }
}