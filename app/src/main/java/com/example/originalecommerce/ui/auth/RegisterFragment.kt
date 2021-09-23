package com.example.originalecommerce.ui.auth

import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.originalecommerce.R
import com.example.originalecommerce.databinding.FragmentLogInBinding
import com.example.originalecommerce.databinding.FragmentRegisterBinding
import com.example.originalecommerce.viewmodels.AuthViewModel
import com.example.orignal_ecommerce_manger.util.StatusResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding?=null
    private val binding get() = _binding!!

    private val authViewModek by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentRegisterBinding.inflate(layoutInflater)
        setUPpView()
        return binding.root
    }

    private fun setUPpView(){
        binding.btnRegfragRegietr.setOnClickListener {
            register()
        }
        binding.btnRegisterfragBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnRegisterfragSinggoogle.setOnClickListener {

        }
        authViewModek.isRegister.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.Error ->{
                    binding.proRegisterfrag.visibility=View.INVISIBLE
                    Toast.makeText(requireActivity(),"${it.masg}",Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.Success ->{
                    binding.proRegisterfrag.visibility=View.INVISIBLE
                    findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
                }
                it is StatusResult.Loading ->{
                    binding.proRegisterfrag.visibility=View.VISIBLE
                }
            }
        })
    }

    private fun  register()
    {
        val name=binding.etRegisterfragName.text.toString()
        val email=binding.etRegisterfragEmail.text.toString()
        val pass=binding.etRegisterfragPass.text.toString()
        val confPass=binding.etRegisterfragConfirmpass.text.toString()

        if (name.trim().isEmpty())
        {
            binding.etRegisterfragName.error="Name require"
            binding.etRegisterfragName.requestFocus()
            return
        }
        if (email.trim().isEmpty())
        {
            binding.etRegisterfragEmail.error="Email require"
            binding.etRegisterfragEmail.requestFocus()
            return
        }
        if (pass.trim().isEmpty())
        {
            binding.etRegisterfragPass.error="Password require"
            binding.etRegisterfragPass.requestFocus()
            return
        }
        if (confPass.trim().isEmpty())
        {
            binding.etRegisterfragConfirmpass.error="Confirmed password require"
            binding.etRegisterfragConfirmpass.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.etRegisterfragEmail.error="Invalid Email"
            binding.etRegisterfragEmail.requestFocus()
            return
        }
        if (pass.length<6)
        {
            binding.etRegisterfragPass.error="password is too weak"
            binding.etRegisterfragPass.requestFocus()
            return
        }
        if (confPass!=pass)
        {
            binding.etRegisterfragConfirmpass.error="Is not Correct"
            binding.etRegisterfragConfirmpass.requestFocus()
            return
        }

        authViewModek.register(email,pass,name)

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}














