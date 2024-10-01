package com.example.jollycat.ui.aboutus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jollycat.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.jollycat.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val jollyCatMarker = LatLng(-6.20175, 106.78208)
        mMap.addMarker(MarkerOptions().position(jollyCatMarker).title("JollyCat’s Store"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jollyCatMarker, 10f))
    }
}