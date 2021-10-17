package com.example.originalecommerce.ui.body

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentPaymentBinding
import com.example.originalecommerce.databinding.FragmentUserBinding
import com.example.originalecommerce.ui.auth.AuthActivity
import com.example.originalecommerce.viewmodels.AuthViewModel
import com.example.originalecommerce.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding?=null
    private val binding get() = _binding!!
    lateinit var user:FirebaseUser
    private val mAuthViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding= FragmentUserBinding.inflate(layoutInflater)
        user=mAuthViewModel.currentUser!!
        setupClicks()
        return binding.root
    }

    private fun setupClicks(){
        binding.imgUserUser.load(user.photoUrl){
            error(R.drawable.ic_person)
        }
        binding.tvNameUser.setText(user.displayName)
        binding.imgLogoutUser.setOnClickListener{
            mAuthViewModel.logOut()
            requireContext().startActivity(Intent(requireContext(),AuthActivity::class.java))
            requireActivity().finish()
        }
        binding.tvLogoutUser.setOnClickListener{
            mAuthViewModel.logOut()
            requireContext().startActivity(Intent(requireContext(),AuthActivity::class.java))
            requireActivity().finish()
        }

        binding.btnPaymeth.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_paymentMethodFragment)
        }

        binding.btnBymeth.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_paymentMethodFragment)
        }

        binding.btnBackUser.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnInfoorder.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_orderInfoFragment)
        }
        binding.tvInfoorder.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_orderInfoFragment)
        }
        binding.tvSetting.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_settingFragment)
        }
        binding.btnSetting.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_settingFragment)
        }
        binding.btnHelp.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_helpFragment)
        }
        binding.tvHelp.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_helpFragment)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}