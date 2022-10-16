package com.web.testtask.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.web.testtask.R
import com.web.testtask.core.basefragment.BaseFragment
import com.web.testtask.databinding.FragmentDetailsBinding
import com.web.testtask.presentation.viewmodel.DetailsViewModel
import com.web.testtask.util.NetworkResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details),
    OnMapReadyCallback {
    private val viewModel: DetailsViewModel by viewModel()

    private val args: DetailsFragmentArgs by navArgs()
    private val city by lazy {
        args.city
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val map = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this)
        viewModel.getWeather(city.coord)
        viewModel.weatherResponse.observe(viewLifecycleOwner) { response ->
            binding.response = response
            if (response is NetworkResult.Success)
                binding.weather = response.data
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(city.coord.lat, city.coord.lon)
        val marker =
            MarkerOptions().position(
                latLng
            )

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                10f
            )
        )
        googleMap.addMarker(marker)
    }
}