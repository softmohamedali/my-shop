package com.example.originalecommerce.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private var _binding:FragmentNotificationBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentNotificationBinding.inflate(layoutInflater)
        binding.btnBackNotifi.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}