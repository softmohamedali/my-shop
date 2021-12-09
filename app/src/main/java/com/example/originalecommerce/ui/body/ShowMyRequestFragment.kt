package com.example.originalecommerce.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.originalecommerce.R
import com.example.originalecommerce.adapters.RequestItemAdapter
import com.example.originalecommerce.databinding.FragmentShowMyOrderBinding
import com.example.originalecommerce.databinding.FragmentShowMyRequestBinding
import com.example.originalecommerce.models.AllREquests
import com.example.originalecommerce.models.Product


class ShowMyRequestFragment : Fragment() {
    private var _binding: FragmentShowMyRequestBinding?=null
    private val binding get() = _binding!!
    private val requestsAdapter by lazy { RequestItemAdapter() }
    private val navArgs by navArgs<ShowMyRequestFragmentArgs>()
    private lateinit var allREquests: AllREquests
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentShowMyRequestBinding.inflate(layoutInflater)
        allREquests=navArgs.requests
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.btnBackRequests.setOnClickListener {
            findNavController().popBackStack()
        }
        setUpRecy()
        requestsAdapter.setData(allREquests.requests!!)
    }

    private fun setUpRecy() {
        binding.recyShowrequests.apply {
            adapter=requestsAdapter
            layoutManager= GridLayoutManager(requireActivity(),2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}