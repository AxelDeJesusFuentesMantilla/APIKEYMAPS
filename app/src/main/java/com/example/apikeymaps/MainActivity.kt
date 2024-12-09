package com.example.apikeymaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.apikeymaps.ui.theme.ApiKeyMapsTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : ComponentActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    // List to store marker positions
    private val markers = mutableListOf<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the MapView
        mapView = MapView(this)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        setContent {
            ApiKeyMapsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapContainer(modifier = Modifier.padding(innerPadding), mapView = mapView)
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Set a default location (e.g., CDMX)
        val defaultLocation = LatLng(19.4326, -99.1332)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))

        // Restore markers if any exist
        restoreMarkers()

        // Add a marker on map click and store its position
        googleMap.setOnMapClickListener { latLng ->
            addMarker(latLng)
        }
    }

    private fun addMarker(latLng: LatLng) {
        // Add marker to the map
        val marker = MarkerOptions().position(latLng).title("New Marker")
        googleMap.addMarker(marker)

        // Save the marker position to the list
        markers.add(latLng)
    }

    private fun restoreMarkers() {
        // Iterate over the saved markers and re-add them to the map
        for (latLng in markers) {
            val marker = MarkerOptions().position(latLng).title("Restored Marker")
            googleMap.addMarker(marker)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}

@Composable
fun MapContainer(modifier: Modifier = Modifier, mapView: MapView) {
    // Render the MapView inside a Compose container
    AndroidView(
        factory = { mapView },
        modifier = modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    ApiKeyMapsTheme {
        // Preview doesn't show the actual map; this is just for layout purposes
    }
}
