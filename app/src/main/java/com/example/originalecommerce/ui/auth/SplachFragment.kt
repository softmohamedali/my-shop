package com.example.originalecommerce.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentSplachBinding
import com.example.originalecommerce.ui.body.BodyActivity
import com.example.originalecommerce.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplachFragment : Fragment() {
    private var _binding:FragmentSplachBinding?=null
    private val binding get() = _binding!!

    private val authViewModek by viewModels<AuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSplachBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            delay(2000)
            if (authViewModek.currentUser!=null)
            {
                startActivity(Intent(requireActivity(), BodyActivity::class.java))
                requireActivity().finish()
            }
            else{
                findNavController().navigate(R.id.action_splachFragment_to_logInFragment)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}