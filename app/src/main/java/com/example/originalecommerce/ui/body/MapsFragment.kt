package com.example.originalecommerce.ui.body

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentMainBodyBinding
import com.example.originalecommerce.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    lateinit var lat:String
    lateinit var long:String
    private var _binding: FragmentMapsBinding? = null
    private val binidng get() = _binding!!
    private val callback = OnMapReadyCallback { googleMap ->

        lat="30.027372718654096"
        long="31.254347027569043"
        val sydney = LatLng(30.027372718654096, 31.254347027569043)
        var marker=googleMap.addMarker(MarkerOptions().position(sydney))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                googleMap.uiSettings.apply {
            isZoomControlsEnabled=true
        }

        googleMap.setOnMapClickListener {
            marker?.remove()
            marker=googleMap.addMarker(MarkerOptions().position(it))
            lat=it.latitude.toString()
            long=it.longitude.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMapsBinding.inflate(layoutInflater)
        binidng.fabCheakLocation.setOnClickListener {
            val action=MapsFragmentDirections.actionMapsFragmentToOrderInfoFragment(long,lat)
            findNavController().navigate(action)
            val toast=Toast(requireActivity())
            toast.duration=Toast.LENGTH_SHORT
            toast.setGravity(Gravity.CENTER,0,0)
            toast.view=LayoutInflater.from(requireActivity())
                .inflate(R.layout.layout_succes_location,null,false)
            toast.show()
        }
        return binidng.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}